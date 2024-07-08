package fk.page.finekorea.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "quote")
public class QuoteEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String phone;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 50, nullable = true)
    private String address;
    @Column(length = 50, nullable = true)
    private String businessName;
    @Column(length = 50, nullable = true)
    private String businessDay;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String content;

    @Builder
    public QuoteEntity(Long id, String name, String phone, String email, String address
                        , String businessName, String businessDay, String content){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.businessName = businessName;
        this.businessDay = businessDay;
        this.content = content;
    }

    public Long getId(){
        return id;
    }

    public String getBusinessDay(){
        return businessDay;
    }

    public String getContent(){
        return content;
    }

    public String getName(){
        if(name != null && name.length() > 0){
            return name.charAt(0) + "xx";
        }
        return name;
    }
    public String getPhone(){
        if(phone != null && phone.length() > 0){
            return phone.substring(0, 4) + "xxx";
        }
        return phone;
    }
    public String getEmail(){
        if(email != null && email.length() > 0){
            return email.charAt(0) + "xx@xxx.x";
        }
        return email;
    }
    public String getAddress(){
        if(address != null && address.length() > 0){
            return address.substring(0, 3) + "xx";
        }
        return address;
    }
    public String getBusinessName(){
        if(businessName != null && businessName.length() > 0){
            return businessName.charAt(0) + "xx";
        }
        return businessName;
    }

}
