package fk.page.finekorea.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fk.page.finekorea.dto.QuestionCommentDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "qusetioncomment")
public class QuestionComment extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ninkName;
    private String password;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;
    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private QuestionEntity questionEntity;

    public QuestionCommentDto toEntity(){
        QuestionCommentDto questionCommentDto = QuestionCommentDto.builder()
                .id(id)
                .ninkName(ninkName)
                .password(password)
                .comment(comment)
                .question(questionEntity)
                .createdDate(getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .modifiedDate(getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .build();
        return questionCommentDto;
    }
}
