package fk.page.finekorea.service;

import fk.page.finekorea.dto.QuoteDto;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 견적 문의 스팸 방지: 허니팟, 작성 시간, 서버 입력 검증, IP별 횟수 제한.
 */
@Component
public class QuoteSpamGuard {

    public enum Result {
        OK,           // 정상
        BOT,          // 봇으로 판단 (저장/메일 없이 조용히 무시)
        INVALID,      // 입력 오류 (사용자에게 안내)
        RATE_LIMITED  // 짧은 시간에 너무 많이 보냄
    }

    // 같은 IP에서 WINDOW_MS 동안 허용되는 최대 제출 횟수
    private static final int MAX_PER_WINDOW = 3;
    private static final long WINDOW_MS = 10 * 60 * 1000L;
    // 페이지를 연 뒤 이 시간(ms)보다 빨리 제출하면 봇으로 판단
    private static final long MIN_FILL_MS = 3_000L;
    // 페이지를 연 뒤 이 시간이 지난 제출은 오래된 토큰으로 간주 (24시간)
    private static final long MAX_FILL_MS = 24L * 60 * 60 * 1000L;

    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern HANGUL = Pattern.compile(".*[가-힣].*", Pattern.DOTALL);
    private static final Pattern LINK = Pattern.compile("(?i)(https?://|www\\.)");

    private final Map<String, Deque<Long>> hits = new ConcurrentHashMap<>();

    public Result check(QuoteDto dto, String honeypot, String formTs, String ip) {
        // 1) 허니팟: 사람에게는 보이지 않는 칸이 채워져 있으면 봇
        if (honeypot != null && !honeypot.isBlank()) {
            return Result.BOT;
        }

        // 2) 작성 시간: JS가 페이지 로드 시각을 넣음. 없거나 너무 빠르거나 비정상이면 봇
        long now = System.currentTimeMillis();
        long opened;
        try {
            opened = Long.parseLong(formTs == null ? "" : formTs.trim());
        } catch (NumberFormatException e) {
            return Result.BOT;
        }
        long elapsed = now - opened;
        if (elapsed < MIN_FILL_MS || elapsed > MAX_FILL_MS) {
            return Result.BOT;
        }

        // 3) 서버 입력 검증
        if (!validFields(dto)) {
            return Result.INVALID;
        }

        // 4) IP별 횟수 제한 (검증을 통과한 요청만 집계)
        if (!allow(ip, now)) {
            return Result.RATE_LIMITED;
        }
        return Result.OK;
    }

    private boolean validFields(QuoteDto d) {
        String name = trim(d.getName());
        String phone = trim(d.getPhone()).replace("-", "").replace(" ", "");
        String email = trim(d.getEmail());
        String content = trim(d.getContent());

        if (name.isEmpty() || name.length() > 30) return false;
        if (!phone.matches("\\d{9,11}")) return false;
        if (email.length() > 100 || !EMAIL.matcher(email).matches()) return false;
        if (content.length() > 2000) return false;
        if (trim(d.getAddress()).length() > 100) return false;
        if (trim(d.getBusinessName()).length() > 100) return false;
        if (trim(d.getBusinessDay()).length() > 30) return false;

        // 이름 또는 내용에 한글이 하나도 없으면 거부 (해외 자동 스팸 차단)
        boolean hasHangul = HANGUL.matcher(name).matches() || HANGUL.matcher(content).matches();
        if (!hasHangul) return false;

        // 링크가 2개 이상이면 스팸으로 판단
        if (LINK.matcher(content).results().count() >= 2) return false;
        return true;
    }

    private synchronized boolean allow(String ip, long now) {
        // 오래된 IP 기록 정리 (메모리 증가 방지)
        if (hits.size() > 10_000) {
            hits.entrySet().removeIf(e -> e.getValue().isEmpty() || now - e.getValue().peekLast() > WINDOW_MS);
        }
        Deque<Long> q = hits.computeIfAbsent(ip == null ? "unknown" : ip, k -> new ArrayDeque<>());
        while (!q.isEmpty() && now - q.peekFirst() > WINDOW_MS) {
            q.pollFirst();
        }
        if (q.size() >= MAX_PER_WINDOW) {
            return false;
        }
        q.addLast(now);
        return true;
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
