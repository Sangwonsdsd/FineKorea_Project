package fk.page.finekorea.dto;


import fk.page.finekorea.domain.entity.QuestionComment;
import fk.page.finekorea.domain.entity.QuestionEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCommentDto {
    private Long id;
    private String ninkName;
    private String password;
    private String comment;
    private QuestionEntity question;
    private String createdDate;
    private String modifiedDate;

    public QuestionComment toEntity(){
        QuestionComment questionComment = QuestionComment.builder()
                .id(id)
                .ninkName(ninkName)
                .password(password)
                .comment(comment)
                .questionEntity(question)
                .build();

        return questionComment;
    }
}
