package com.mirceanealcos.confruntarea.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;


/**
 * Monster DTO used for better data transfer between backend and frontend
 */
public class MonsterDTO implements Serializable {
    private String name;
    private Integer level;
    private Integer hp;
    private Integer baseDamage;
    private Integer moneyReward;
    private Integer expReward;
    private String picture;
    private String nameColor;

    public MonsterDTO() {
    }

    public MonsterDTO(String name, Integer level, Integer hp, Integer baseDamage, Integer moneyReward, Integer expReward, String picture, String nameColor) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.baseDamage = baseDamage;
        this.moneyReward = moneyReward;
        this.expReward = expReward;
        this.picture = picture;
        this.nameColor = nameColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(Integer baseDamage) {
        this.baseDamage = baseDamage;
    }

    public Integer getMoneyReward() {
        return moneyReward;
    }

    public void setMoneyReward(Integer moneyReward) {
        this.moneyReward = moneyReward;
    }

    public Integer getExpReward() {
        return expReward;
    }

    public void setExpReward(Integer expReward) {
        this.expReward = expReward;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return name == null && level == null && hp == null && baseDamage == null && moneyReward == null && expReward == null && picture == null && nameColor == null;
    }

    public boolean hasPopulatedFields() {
        return name != null || level != null || hp != null || baseDamage != null || moneyReward != null || expReward != null || picture != null || nameColor != null;
    }

    public boolean hasEmptyFields() {
        return name == null || level == null || hp == null || baseDamage == null || moneyReward == null || expReward == null || picture == null || nameColor == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonsterDTO entity = (MonsterDTO) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.level, entity.level) &&
                Objects.equals(this.hp, entity.hp) &&
                Objects.equals(this.baseDamage, entity.baseDamage) &&
                Objects.equals(this.moneyReward, entity.moneyReward) &&
                Objects.equals(this.expReward, entity.expReward) &&
                Objects.equals(this.picture, entity.picture) &&
                Objects.equals(this.nameColor, entity.nameColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, hp, baseDamage, moneyReward, expReward, picture, nameColor);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "level = " + level + ", " +
                "hp = " + hp + ", " +
                "baseDamage = " + baseDamage + ", " +
                "moneyReward = " + moneyReward + ", " +
                "expReward = " + expReward + ", " +
                "picture = " + picture + ", " +
                "nameColor = " + nameColor + ")";
    }
}
