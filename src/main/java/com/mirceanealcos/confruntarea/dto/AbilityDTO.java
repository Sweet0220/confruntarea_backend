package com.mirceanealcos.confruntarea.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirceanealcos.confruntarea.entity.enums.AbilityType;

import java.io.Serializable;
import java.util.Objects;

/**
 * Ability DTO used for better data transfer between backend and frontend
 */
public class AbilityDTO implements Serializable {
    private String name;
    private AbilityType type;
    private Integer healing;
    private Integer damage;
    private String picture;
    private Integer manaCost;

    public AbilityDTO() {
    }

    public AbilityDTO(String name, AbilityType type, Integer healing, Integer damage, String picture, Integer manaCost) {
        this.name = name;
        this.type = type;
        this.healing = healing;
        this.damage = damage;
        this.picture = picture;
        this.manaCost = manaCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbilityType getType() {
        return type;
    }

    public void setType(AbilityType type) {
        this.type = type;
    }

    public Integer getHealing() {
        return healing;
    }

    public void setHealing(Integer healing) {
        this.healing = healing;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getManaCost() {
        return manaCost;
    }

    public void setManaCost(Integer manaCost) {
        this.manaCost = manaCost;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.name == null && this.type == null && this.healing == null && this.damage == null && this.picture == null && this.manaCost == null;
    }

    public boolean hasEmptyFields() {
        return this.name == null || this.type == null || this.healing == null || this.damage == null || this.picture == null || this.manaCost == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbilityDTO entity = (AbilityDTO) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.type, entity.type) &&
                Objects.equals(this.healing, entity.healing) &&
                Objects.equals(this.damage, entity.damage) &&
                Objects.equals(this.picture, entity.picture) &&
                Objects.equals(this.manaCost, entity.manaCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, healing, damage, picture, manaCost);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "type = " + type + ", " +
                "healing = " + healing + ", " +
                "damage = " + damage + ", " +
                "picture = " + picture + ", " +
                "manaCost = " + manaCost + ")";
    }
}
