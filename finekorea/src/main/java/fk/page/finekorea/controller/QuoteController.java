package fk.page.finekorea.controller;


import fk.page.finekorea.dto.QuoteDto;
import fk.page.finekorea.service.QuoteService;
import fk.page.finekorea.service.QuoteSpamGuard;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuoteController {
    @Autowired
    private QuoteService quoteService;

    @Autowired
    private QuoteSpamGuard quoteSpamGuard;

    //견적문의 페이지
    @GetMapping("/guest/quote")
    public String quote() {
        return "quoteContact/quoteContact";
    }

    @PostMapping("/guest/quote")
    public String quote(QuoteDto quoteDto,
                        @RequestParam(value = "website", required = false) String honeypot,
                        @RequestParam(value = "formTs", required = false) String formTs,
                        HttpServletRequest request){
        QuoteSpamGuard.Result result = quoteSpamGuard.check(quoteDto, honeypot, formTs, request.getRemoteAddr());
        switch (result) {
            case OK:
                quoteService.savePost(quoteDto);
                quoteService.sendEmail(quoteDto);
                return "redirect:/guest/quoteList";
            case BOT:
                // 봇에게는 성공한 것처럼 보이게 하고 저장/메일 전송은 하지 않음
                return "redirect:/guest/quoteList";
            case RATE_LIMITED:
                return "redirect:/guest/quote?error=limit";
            default:
                return "redirect:/guest/quote?error=invalid";
        }
    }

    //견적문의 리스트 페이지
    @GetMapping("/guest/quoteList")
    public String quoteList(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {


        model.addAttribute("quoteList", quoteService.getQuotelist(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))));


        return "quoteContact/quoteContactList";
    }
}
