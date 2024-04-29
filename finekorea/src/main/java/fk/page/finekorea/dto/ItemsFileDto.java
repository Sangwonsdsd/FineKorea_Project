package fk.page.finekorea.dto;


import fk.page.finekorea.domain.entity.ItemsEntity;
import fk.page.finekorea.domain.entity.ItemsFileEntity;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemsFileDto {
    private Long id;
    private String originalName; //원래 파일 이름
    private String changeName; //저장될 파일 이름
    private String filePath;
    private String folderPath; //파일 패스
    private Long size; //파일 사이즈
    private ItemsEntity itemsEntity;

    public ItemsFileEntity toEntity(){
        ItemsFileEntity itemsFileEntity = ItemsFileEntity.builder()
                .id(id)
                .originalName(originalName)
                .changeName(changeName)
                .filePath(filePath)
                .folderPath(folderPath)
                .size(size)
                .itemsEntity(itemsEntity)
                .build();
        return itemsFileEntity;
    }

    public ItemsFileDto(String originalName, String changeName, String filePath, String folderPath, Long size, ItemsEntity itemsEntity){
        this.originalName = originalName;
        this.changeName = changeName;
        this.filePath = filePath;
        this.folderPath = folderPath;
        this.size = size;
        this.itemsEntity = itemsEntity;
    }
}
