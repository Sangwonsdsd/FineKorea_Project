package fk.page.finekorea.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "uploadfile")
public class UploadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName; //원래 파일 이름
    private String changeName; //저장될 파일 이름
    private String filePath;
    private String folderPath; //파일 패스
    private Long size; //파일 사이즈

}
