package fk.page.finekorea.controller;


import fk.page.finekorea.dto.QuoteDto;
import fk.page.finekorea.service.QuoteService;
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

    //견적문의 페이지
    @GetMapping("/guest/quote")
    public String quote() {
        return "quoteContact/quoteContact";
    }

    @PostMapping("/guest/quote")
    public String quote(QuoteDto quoteDto){
        quoteService.savePost(quoteDto);
        quoteService.sendEmail(quoteDto);
        return "redirect:/guest/quoteList";
    }

    //견적문의 리스트 페이지
    @GetMapping("/guest/quoteList")
    public String quoteList(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {


        model.addAttribute("quoteList", quoteService.getQuotelist(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))));


        return "quoteContact/quoteContactList";
    }
}
