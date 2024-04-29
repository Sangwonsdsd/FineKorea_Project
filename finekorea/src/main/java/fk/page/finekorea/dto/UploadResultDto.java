package fk.page.finekorea.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UploadResultDto {
    private Long id;
    private String originalName; //원래 파일 이름
    private String changeName; //저장될 파일 이름
    private String filePath;
    private String folderPath; //파일 패스
    private Long size; //파일 사이즈

    public UploadResultDto(String originalName, String changeName, String filePath, String folderPath, Long size){
        this.originalName = originalName;
        this.changeName = changeName;
        this.filePath = filePath;
        this.folderPath = folderPath;
        this.size = size;
    }
}
