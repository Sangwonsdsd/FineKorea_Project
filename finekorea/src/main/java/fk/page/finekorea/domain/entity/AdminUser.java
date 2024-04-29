package fk.page.finekorea.domain.entity;


import fk.page.finekorea.dto.AdminUserDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "adminUser")
@Builder
public class AdminUser extends TimeEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String adminId;
    private String password;
    private String adminName;

    public AdminUserDto toEntity(){
        AdminUserDto adminUserDto = AdminUserDto.builder()
                .id(id)
                .adminId(adminId)
                .adminName(adminName)
                .createdDate(getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .modifiedDate(getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .build();
        return adminUserDto;
    }

}
