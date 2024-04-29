package fk.page.finekorea.dto;


import fk.page.finekorea.domain.entity.QuestionComment;
import fk.page.finekorea.domain.entity.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long num;
    private String ninkName;
    private String password;
    private String title;
    private String content;
    private int view;
    private boolean writingCheck;
    private String fileList;
    private List<QuestionComment> comments;
    private String createdDate;
    private String modifiedDate;

    public QuestionEntity toEntity(){
        QuestionEntity questionEntity = QuestionEntity.builder()
                .num(num)
                .ninkName(ninkName)
                .password(password)
                .title(title)
                .content(content)
                .view(view)
                .writingCheck(writingCheck)
                .fileList(fileList)
                .comments(comments)
                .build();
        return questionEntity;
    }

}
