package fk.page.finekorea.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", ""})
    public String homeForm(){
        return "common/main";
    }

    @GetMapping("/guest/about")
    public String aboutForm(){
        return "common/aboutUs";
    }

}
