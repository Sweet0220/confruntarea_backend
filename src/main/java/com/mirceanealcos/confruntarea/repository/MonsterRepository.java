package com.mirceanealcos.confruntarea.repository;

import com.mirceanealcos.confruntarea.entity.Monster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * JPA Repository used to perform CRUD operations on monster entities
 */
@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {

    List<Monster> findByLevel(Integer level);

    Monster findByName(String name);

}
