package fk.page.finekorea.dto;


import fk.page.finekorea.domain.entity.AdminUser;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class AdminUserDto {
    private Long id;
    private String adminId;
    private String password;
    private String adminName;
    private String createdDate;
    private String modifiedDate;


    public AdminUser toEntity(){
        AdminUser adminUser = AdminUser.builder()
                .id(id)
                .adminId(adminId)
                .adminName(adminName)
                .build();
        return adminUser;
    }


}
