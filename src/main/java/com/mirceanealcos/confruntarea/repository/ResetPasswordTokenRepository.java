package com.mirceanealcos.confruntarea.repository;

import com.mirceanealcos.confruntarea.security.model.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {

    void deleteByUserUsername(String username);
    ResetPasswordToken getByUserUsername(String username);

}
