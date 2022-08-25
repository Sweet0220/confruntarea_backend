package com.mirceanealcos.confruntarea.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * Smaller user DTO used for registering new users
 */
public class UserRegisterDTO implements Serializable {
    private String username;
    private String email;
    private String password;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegisterDTO entity = (UserRegisterDTO) o;
        return Objects.equals(this.username, entity.username) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.password, entity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "username = " + username + ", " +
                "email = " + email + ", " +
                "password = " + password + ")";
    }
}
