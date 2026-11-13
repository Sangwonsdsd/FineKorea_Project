// 메인 화면 상단 사진: 5초마다 부드럽게 다음 사진으로 전환
document.addEventListener('DOMContentLoaded', function () {
    const slides = document.querySelectorAll('#heroSlider .hero-slide');
    if (slides.length < 2) return;
    // 모션 줄이기 설정이어도 5초마다 전환은 함 (이 경우 CSS가 페이드 없이 바로 바꿈)
    let current = 0;
    const INTERVAL_MS = 5000;

    function show(next) {
        slides[current].classList.remove('active');
        current = next;
        slides[current].classList.add('active');
    }

    let timer = setInterval(function () {
        show((current + 1) % slides.length);
    }, INTERVAL_MS);

    // 탭이 숨겨져 있는 동안은 멈춤 (불필요한 동작 방지)
    document.addEventListener('visibilitychange', function () {
        clearInterval(timer);
        if (!document.hidden) {
            timer = setInterval(function () {
                show((current + 1) % slides.length);
            }, INTERVAL_MS);
        }
    });
});
