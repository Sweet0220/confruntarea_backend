package com.mirceanealcos.confruntarea.repository;

import com.mirceanealcos.confruntarea.entity.ChampionOwnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * JPA Repository used to perform CRUD operations on the link table entities between users and champions
 */
@Repository
public interface ChampionOwnershipRepository extends JpaRepository<ChampionOwnership, Long> {

    void deleteByUserUsername(String username);

    void deleteByChampionName(String name);

    List<ChampionOwnership> findByUserUsername(String username);

    List<ChampionOwnership> findByChampionName(String name);

}
