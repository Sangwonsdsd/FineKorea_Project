package fk.page.finekorea.domain.repository;


import fk.page.finekorea.domain.entity.UploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveFileRepository extends JpaRepository<UploadEntity, Long> {
}
