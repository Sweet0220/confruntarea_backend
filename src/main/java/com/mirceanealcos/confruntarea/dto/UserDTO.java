package com.mirceanealcos.confruntarea.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * User DTO for better data transfer between backend and frontend
 */
public class UserDTO implements Serializable {
    private String username;
    private String password;
    private String email;
    private Integer funds;
    private String picture;
    private Integer level;
    private Integer exp;
    private String role;
    private boolean isEnabled;

    public UserDTO() {
    }

    public UserDTO(String username,String password, String email, Integer funds, String picture, Integer level, Integer exp, String role, boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.funds = funds;
        this.picture = picture;
        this.level = level;
        this.exp = exp;
        this.role = role;
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public Integer getFunds() {
        return funds;
    }

    public void setFunds(Integer funds) {
        this.funds = funds;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
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
        UserDTO entity = (UserDTO) o;
        return Objects.equals(this.username, entity.username) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.funds, entity.funds) &&
                Objects.equals(this.picture, entity.picture) &&
                Objects.equals(this.level, entity.level) &&
                Objects.equals(this.exp, entity.exp) &&
                Objects.equals(this.password, entity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, funds, picture, level, exp);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "username = " + username + ", " +
                "password = " + password + ", " +
                "email = " + email + ", " +
                "funds = " + funds + ", " +
                "picture = " + picture + ", " +
                "level = " + level + ", " +
                "exp = " + exp + ")";
    }
}
