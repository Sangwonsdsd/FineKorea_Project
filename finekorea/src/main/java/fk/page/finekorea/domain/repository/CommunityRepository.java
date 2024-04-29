package fk.page.finekorea.domain.repository;


import fk.page.finekorea.domain.entity.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    Page<CommunityEntity> findByTitleContaining(String keyword, Pageable pageable);
}
