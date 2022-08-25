package com.mirceanealcos.confruntarea.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mirceanealcos.confruntarea.security.model.ResetPasswordToken;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * The User entity from the DB
 * Relationships: items (many-to-many), champions (many-to-many)
 */
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private Integer funds;
    private String picture;
    private Integer level;
    private Integer exp;
    @OneToOne(mappedBy = "user")
    private ResetPasswordToken passwordToken;

    public ResetPasswordToken getPasswordToken() {
        return passwordToken;
    }

    public void setPasswordToken(ResetPasswordToken passwordToken) {
        this.passwordToken = passwordToken;
    }

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonInclude
    private Set<ChampionOwnership> championOwnerships = new HashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private Set<ItemOwnership> itemOwnerships = new HashSet<>();

    public User() {

    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public Set<ChampionOwnership> getChampionOwnerships() {
        return championOwnerships;
    }

    public void setChampionOwnerships(Set<ChampionOwnership> championOwnerships) {
        this.championOwnerships = championOwnerships;
    }

    public Set<ItemOwnership> getItemOwnerships() {
        return itemOwnerships;
    }

    public void setItemOwnerships(Set<ItemOwnership> itemOwnerships) {
        this.itemOwnerships = itemOwnerships;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", funds=" + funds +
                ", picture='" + picture + '\'' +
                ", level=" + level +
                ", exp=" + exp +
                ", championOwnerships=" + championOwnerships +
                ", itemOwnerships=" + itemOwnerships +
                '}';
    }
}
