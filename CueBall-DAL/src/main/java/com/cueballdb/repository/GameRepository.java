package com.cueballdb.repository;

import com.cueballdb.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface GameRepository extends JpaRepository<Game, Integer>, GameCustomRepository{
    Game findByTitle(String title);

    @Query(value = "SELECT * FROM `game` AS g WHERE FIND_IN_SET(g.id, :gameIds) > 0", nativeQuery = true)
    List<Game> findAllByIds(@Param("gameIds") String gameIds);

//    List<Game> findAllByEnableTrue();

    @Query("SELECT g FROM Game g where g.enable = true AND g.delete = false ORDER BY g.id DESC")
    List<Game> findAllByEnableTrueAndDeleteFalse();

    @Query("SELECT COUNT(*) FROM Game g where g.delete = false")
    long count();

}


