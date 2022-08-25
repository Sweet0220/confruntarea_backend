package com.mirceanealcos.confruntarea.repository;

import com.mirceanealcos.confruntarea.entity.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * JPA Repository used to perform CRUD operations on ability entities
 */
@Repository
public interface AbilityRepository extends JpaRepository<Ability, Long> {

    Ability findByName(String name);

    List<Ability> findByChampionName(String name);


}
