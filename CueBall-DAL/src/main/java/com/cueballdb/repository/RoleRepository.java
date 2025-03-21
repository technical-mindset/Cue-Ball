package com.cueballdb.repository;


import com.cueballdb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query ("SELECT b FROM Role b ORDER BY b.id DESC")
    List<Role> findAll();


    @Query("SELECT b FROM Role b WHERE b.id IN :roleList")
    List<Role> findByRoleList(@Param("roleList") List<Integer> roleList);

    @Query ("SELECT b FROM Role b where b.name<>'ROLE_SUPER_ADMIN' ORDER BY b.id DESC")
    List<Role> findRolesByName();
}
