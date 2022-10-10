package com.mirceanealcos.confruntarea.repository;

import com.mirceanealcos.confruntarea.entity.Champion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * JPA Repository used to perform CRUD operations on champion entities
 */
@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {

    Champion findByName(String name);

    List<Champion> findByOwnershipsUserUsername(String username);

    List<Champion> findByOrderByNameAsc(Pageable pageable);

}
