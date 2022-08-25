package com.mirceanealcos.confruntarea.repository;

import com.mirceanealcos.confruntarea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * JPA Repository used to perform CRUD operations on user entities
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findByEmail(String email);

}
