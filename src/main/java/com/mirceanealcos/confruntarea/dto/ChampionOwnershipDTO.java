package com.mirceanealcos.confruntarea.dto;

import com.mirceanealcos.confruntarea.entity.Champion;
import com.mirceanealcos.confruntarea.entity.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * Link table (champions-users) DTO used for better data transfer between backend and frontend
 */
public class ChampionOwnershipDTO implements Serializable {
    private Long id;
    private Integer level;
    private Champion champion;
    private User user;

    public ChampionOwnershipDTO() {
    }

    public ChampionOwnershipDTO(Long id, Integer level, Champion champion, User user) {
        this.id = id;
        this.level = level;
        this.champion = champion;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChampionOwnershipDTO entity = (ChampionOwnershipDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.level, entity.level) &&
                Objects.equals(this.champion, entity.champion) &&
                Objects.equals(this.user, entity.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level, champion, user);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "level = " + level + ", " +
                "champion = " + champion + ", " +
                "user = " + user + ")";
    }
}
