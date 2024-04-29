package fk.page.finekorea.dto;


import fk.page.finekorea.domain.entity.QuoteEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QuoteDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String businessName;
    private String businessDay;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public QuoteEntity toEntity(){
        QuoteEntity quoteEntity = QuoteEntity.builder()
                .id(id)
                .name(name)
                .phone(phone)
                .email(email)
                .address(address)
                .businessName(businessName)
                .businessDay(businessDay)
                .content(content)
                .build();
        return quoteEntity;
    }

    @Builder
    public QuoteDto(Long id, String name, String phone, String email, String address
            , String businessName, String businessDay, String content
            , LocalDateTime createDate, LocalDateTime modifiedDate){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.businessName = businessName;
        this.businessDay = businessDay;
        this.content = content;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
