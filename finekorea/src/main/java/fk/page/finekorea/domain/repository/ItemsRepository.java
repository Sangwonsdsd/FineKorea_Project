package fk.page.finekorea.domain.repository;


import fk.page.finekorea.domain.entity.ItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<ItemsEntity, Long> {
    List<ItemsEntity> findByItems(String items);
}
