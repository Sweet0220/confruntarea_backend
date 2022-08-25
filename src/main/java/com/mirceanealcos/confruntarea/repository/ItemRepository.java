package com.mirceanealcos.confruntarea.repository;

import com.mirceanealcos.confruntarea.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * JPA Repository used to perform CRUD operations on item entities
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByType(String type);

    Item findByName(String name);

    List<Item> findByOwnershipsUserId(Long user_id);

}
