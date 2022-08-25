package com.mirceanealcos.confruntarea.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * Champion DTO used for better data transfer between backend and frontend
 */
public class ChampionDTO implements Serializable {
    private String name;
    private Integer hp;
    private Integer baseDamage;
    private Integer price;
    private Integer mana;
    private String picture;
    private String nameColor;

    public ChampionDTO() {
    }

    public ChampionDTO(String name, Integer hp, Integer baseDamage, Integer price, Integer mana, String picture, String nameColor) {
        this.name = name;
        this.hp = hp;
        this.baseDamage = baseDamage;
        this.price = price;
        this.mana = mana;
        this.picture = picture;
        this.nameColor = nameColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMana() {
        return mana;
    }

    public void setMana(Integer mana) {
        this.mana = mana;
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

    public boolean hasEmptyFields() {
        return this.name == null || this.baseDamage == null || this.hp == null || this.mana == null || this.price == null || this.picture == null || this.nameColor == null;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.name == null && this.baseDamage == null && this.hp == null && this.mana == null && this.price == null && this.picture == null && this.nameColor == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChampionDTO entity = (ChampionDTO) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.hp, entity.hp) &&
                Objects.equals(this.baseDamage, entity.baseDamage) &&
                Objects.equals(this.price, entity.price) &&
                Objects.equals(this.mana, entity.mana) &&
                Objects.equals(this.picture, entity.picture) &&
                Objects.equals(this.nameColor, entity.nameColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hp, baseDamage, price, mana, picture, nameColor);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "hp = " + hp + ", " +
                "baseDamage = " + baseDamage + ", " +
                "price = " + price + ", " +
                "mana = " + mana + ", " +
                "picture = " + picture + ", " +
                "nameColor = " + nameColor + ")";
    }
}
