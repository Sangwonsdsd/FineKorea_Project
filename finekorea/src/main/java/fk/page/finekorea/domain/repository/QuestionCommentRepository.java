package fk.page.finekorea.domain.repository;



import fk.page.finekorea.domain.entity.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {
    List<QuestionComment> findByQuestionEntityNum(Long questionEntityNum);
}
