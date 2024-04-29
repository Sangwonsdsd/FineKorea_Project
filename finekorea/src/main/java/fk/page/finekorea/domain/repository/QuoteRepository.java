package fk.page.finekorea.domain.repository;


import fk.page.finekorea.domain.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {
}
