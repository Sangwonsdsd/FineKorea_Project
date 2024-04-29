package fk.page.finekorea.domain.repository;


import fk.page.finekorea.domain.entity.ItemsFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsFileRepository extends JpaRepository<ItemsFileEntity, Long> {
}
