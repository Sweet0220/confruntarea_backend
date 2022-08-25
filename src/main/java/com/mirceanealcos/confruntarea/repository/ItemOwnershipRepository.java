package com.mirceanealcos.confruntarea.repository;

import com.mirceanealcos.confruntarea.entity.ItemOwnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * JPA Repository used to perform CRUD operations on link table entities between users and items
 */
@Repository
public interface ItemOwnershipRepository extends JpaRepository<ItemOwnership, Long> {

    void deleteByUserUsername(String username);

    void deleteByItemName(String name);

    List<ItemOwnership> findByUserUsername(String username);

    List<ItemOwnership> findByItemName(String name);

}
