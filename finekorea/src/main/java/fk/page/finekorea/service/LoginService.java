package fk.page.finekorea.service;


import fk.page.finekorea.domain.entity.AdminUser;
import fk.page.finekorea.domain.repository.AdminUserRepository;
import fk.page.finekorea.dto.AdminUserDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService{
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String password = "hjungcwoo";

    public void createAdminEntity(AdminUserDto adminUserDto){
        AdminUser adminUser = new AdminUser();
        adminUser.setAdminId(adminUserDto.getAdminId());
        adminUser.setPassword(passwordEncoder.encode(adminUserDto.getPassword()));
        adminUser.setAdminName(adminUserDto.getAdminName());
        adminUserRepository.save(adminUser);
    }

    public void checkUser(){

        if(adminUserRepository.findAll().size() == 0){
            AdminUser adminUser = new AdminUser();
            adminUser.setAdminId("hjungcwoo");
            adminUser.setPassword(passwordEncoder.encode(password));
            adminUserRepository.save(adminUser);
        }
    }

    public String checkAdmin(AdminUserDto adminUserDto){
        try {
            AdminUser adminUser = adminUserRepository.findByAdminId(adminUserDto.getAdminId())
                    .orElseThrow(() -> new EntityNotFoundException("회원이 없습니다."));
            if(adminUser != null && passwordEncoder.matches(adminUserDto.getPassword(), adminUser.getPassword())){
                return "로그인성공";
            }else{
                return  "로그아웃실패";
            }
        } catch (EntityNotFoundException ex) {
            return "회원이 없습니다.";
        }

    }

    public AdminUserDto selectAdmin(AdminUserDto adminUserDto) {
        AdminUser adminUser =adminUserRepository.findByAdminId(adminUserDto.getAdminId()).get();
        adminUserDto.toEntity();
        adminUserDto.setPassword(passwordEncoder.encode(adminUserDto.getPassword()));
        return adminUserDto;
    }
}
