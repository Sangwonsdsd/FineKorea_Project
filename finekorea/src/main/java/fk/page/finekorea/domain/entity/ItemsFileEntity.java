package fk.page.finekorea.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fk.page.finekorea.dto.ItemsFileDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "itemfile")
public class ItemsFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName; //원래 파일 이름
    private String changeName; //저장될 파일 이름
    private String filePath;
    private String folderPath; //파일 패스
    private Long size; //파일 사이즈
    @ManyToOne
    @JoinColumn(name = "items_id")
    @JsonIgnore
    private ItemsEntity itemsEntity;

    public ItemsFileDto toEntity(){
        ItemsFileDto itemsFileDto = ItemsFileDto.builder()
                .id(id)
                .originalName(originalName)
                .changeName(changeName)
                .filePath(filePath)
                .folderPath(folderPath)
                .size(size)
                .itemsEntity(itemsEntity)
                .build();
        return itemsFileDto;
    }

}
