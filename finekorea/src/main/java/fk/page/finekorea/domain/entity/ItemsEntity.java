package fk.page.finekorea.domain.entity;


import fk.page.finekorea.dto.ItemsDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "items")
public class ItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String items;
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @OneToMany(mappedBy = "itemsEntity", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<ItemsFileEntity> itemsFileEntityList;
    public ItemsDto toEntity(){
        ItemsDto itemsDto = ItemsDto.builder()
                .id(id)
                .items(items)
                .title(title)
                .content(content)
                .itemsFileEntityList(itemsFileEntityList)
                .build();

        return  itemsDto;
    }
}
