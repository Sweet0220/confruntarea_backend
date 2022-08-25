package com.mirceanealcos.confruntarea.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Represents an entity for the link table between the users and the champions
 */
@Entity
@Table(name = "champion_ownership")
public class ChampionOwnership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer level;

    @ManyToOne
    @JoinColumn(name = "ref_champion")
    @JsonIgnore
    private Champion champion;

    @ManyToOne
    @JoinColumn(name = "ref_user")
    @JsonIgnore
    private User user;

    public ChampionOwnership() {

    }

    public Long getId() {
        return id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ChampionOwnership{" +
                "id=" + id +
                ", level=" + level +
                ", champion=" + champion +
                ", user=" + user +
                '}';
    }

}
