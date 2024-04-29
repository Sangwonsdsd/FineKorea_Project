package fk.page.finekorea.domain.repository;


import fk.page.finekorea.domain.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long>{
    Optional<AdminUser> findByAdminId(String adminId);
}
