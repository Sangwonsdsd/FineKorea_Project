package fk.page.finekorea.controller;



import fk.page.finekorea.dto.AdminUserDto;
import fk.page.finekorea.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    @Autowired
    private LoginService loginService;



    @GetMapping("/guest/loginForm")
    public String loginForm(){
        loginService.checkUser();
        return "common/login";
    }

    @GetMapping("/guest/joinForm")
    public String joinForm(){
        return "common/join";
    }

    @PostMapping("/guest/join")
    public String join(AdminUserDto adminUserDto, Model model){
        loginService.createAdminEntity(adminUserDto);
        return  "redirect:/";
    }

    @PostMapping("/guest/login")
    public String login(AdminUserDto adminUserDto, Model model, HttpSession session){
        if(loginService.checkAdmin(adminUserDto).equals("로그아웃실패") || loginService.checkAdmin(adminUserDto).equals("회원이 없습니다.")){
            model.addAttribute("failText" , loginService.checkAdmin(adminUserDto).equals("fail"));
            return  "common/login";
        }else{
            model.addAttribute("failText" , loginService.checkAdmin(adminUserDto).equals("fail"));
            session.setAttribute("admin", loginService.selectAdmin(adminUserDto));
        }
        return  "common/main";
    }

    @GetMapping("/guest/logout")
    public String logout(HttpSession session, Model model){
        session.removeAttribute("admin");
        model.addAttribute("failText" , "로그아웃 성공");

        return  "common/main";
    }

}
