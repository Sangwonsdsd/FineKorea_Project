package fk.page.finekorea.domain.entity;


import fk.page.finekorea.dto.QuestionDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "question")
public class QuestionEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    private String ninkName;
    private String password;
    @Column(length = 500, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;
    private boolean writingCheck;
    @Column(nullable = true)
    private String fileList;
    @OneToMany(mappedBy = "questionEntity", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<QuestionComment> comments;

    public QuestionDto toEntity(){
        QuestionDto questionDto = QuestionDto.builder()
                .num(num)
                .ninkName(ninkName)
                .password(password)
                .title(title)
                .content(content)
                .view(view)
                .writingCheck(writingCheck)
                .fileList(fileList)
                .comments(comments)
                .modifiedDate(getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .createdDate(getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .build();
        return questionDto;
    }

}
