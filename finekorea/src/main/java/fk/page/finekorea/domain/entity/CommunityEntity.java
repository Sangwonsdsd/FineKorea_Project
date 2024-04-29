package fk.page.finekorea.domain.entity;


import fk.page.finekorea.dto.CommunityDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "community")
public class CommunityEntity extends TimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;
    @Column(length = 500, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(columnDefinition = "VARCHAR(15) default '관리자'", nullable = false)
    private String writer;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;
    @Column(nullable = true)
    private String fileList;

    public CommunityDto toEntity(){
        CommunityDto communityDto = CommunityDto.builder()
                .communityId(communityId)
                .title(title)
                .content(content)
                .writer(writer)
                .view(view)
                .fileList(fileList)
                .createdDate(getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .modifiedDate(getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .build();

        return communityDto;
    }
}
