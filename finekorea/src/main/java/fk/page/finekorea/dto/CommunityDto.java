package fk.page.finekorea.dto;


import fk.page.finekorea.domain.entity.CommunityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDto {
    private Long communityId;
    private String title;
    private String content;
    private String writer;
    private int view;
    private String fileList;
    private String createdDate;
    private String modifiedDate;

    public CommunityEntity toEntity(){
        CommunityEntity communityEntity = CommunityEntity.builder()
                .communityId(communityId)
                .title(title)
                .content(content)
                .writer(writer)
                .view(view)
                .fileList(fileList)
                .build();

        return communityEntity;
    }
}
