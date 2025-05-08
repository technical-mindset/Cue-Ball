package com.cueballdb.repository;

import com.cueballdb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Query("SELECT b FROM User b  ORDER BY b.id DESC")
    Page<User> findAll(Pageable pageable);

    @Query("SELECT b FROM User b where b.id<>'1' ORDER BY b.id DESC")
    Page<User> findUsersById(Pageable pageable);

    @Query(value = "SELECT * FROM users b where b.id <> ?1 and b.username =?2", nativeQuery = true)
    List<User> findUsersByUsername(Integer id, String username);

    @Query("SELECT b FROM User b where b.enable = true ORDER BY b.id DESC")
    List<User> findAllEnable();

    @Query("SELECT b FROM User b JOIN b.roles r WHERE b.enable = true AND r.name NOT IN ('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER') ORDER BY b.id DESC")
    List<User> findAllUsersByRoles();

    @Query("SELECT b FROM User b JOIN b.roles r WHERE b.enable = true AND r.name NOT IN ('ROLE_SUPER_ADMIN', 'ROLE_ADMIN') ORDER BY b.id DESC")
    List<User> findAllUsersForAdmin();
}
