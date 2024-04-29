package fk.page.finekorea.dto;

import fk.page.finekorea.domain.entity.ItemsEntity;
import fk.page.finekorea.domain.entity.ItemsFileEntity;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDto {
    private Long id;
    private String items;
    private String title;
    private String content;
    private List<ItemsFileEntity> itemsFileEntityList;


    public ItemsEntity toEntity(){
        ItemsEntity itemsEntity = ItemsEntity.builder()
                .id(id)
                .items(items)
                .title(title)
                .content(content)
                .itemsFileEntityList(itemsFileEntityList)
                .build();
        return  itemsEntity;
    }
}
