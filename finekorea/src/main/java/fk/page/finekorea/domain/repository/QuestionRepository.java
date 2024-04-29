package fk.page.finekorea.domain.repository;


import fk.page.finekorea.domain.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository <QuestionEntity, Long> {
    Page<QuestionEntity> findByTitleContaining(String keyword, Pageable pageable);
}
