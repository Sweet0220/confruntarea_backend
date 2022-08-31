package com.mirceanealcos.confruntarea.dto;

import com.mirceanealcos.confruntarea.entity.enums.ItemType;

import java.io.Serializable;
import java.util.Objects;


/**
 * Item DTO used for better data transfer between backend and frontend
 */
public class ItemDTO implements Serializable {
    private String name;
    private Integer bonusDamage;
    private Integer bonusHp;
    private Integer price;
    private ItemType type;
    private String picture;
    private String nameColor;

    public ItemDTO() {
    }

    public ItemDTO(String name, Integer bonusDamage, Integer bonusHp, Integer price, ItemType type, String picture, String nameColor) {
        this.name = name;
        this.bonusDamage = bonusDamage;
        this.bonusHp = bonusHp;
        this.price = price;
        this.type = type;
        this.picture = picture;
        this.nameColor = nameColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBonusDamage() {
        return bonusDamage;
    }

    public void setBonusDamage(Integer bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    public Integer getBonusHp() {
        return bonusHp;
    }

    public void setBonusHp(Integer bonusHp) {
        this.bonusHp = bonusHp;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
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
        return this.name == null || this.bonusDamage == null || this.bonusHp == null || this.price == null || this.type == null ||
                this.picture == null || this.nameColor == null;
    }

    public boolean hasPopulatedFields() {
        return this.name != null || this.bonusDamage != null || this.bonusHp != null || this.price != null || this.type != null ||
                this.picture != null || this.nameColor != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO entity = (ItemDTO) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.bonusDamage, entity.bonusDamage) &&
                Objects.equals(this.bonusHp, entity.bonusHp) &&
                Objects.equals(this.price, entity.price) &&
                Objects.equals(this.type, entity.type) &&
                Objects.equals(this.picture, entity.picture) &&
                Objects.equals(this.nameColor, entity.nameColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, bonusDamage, bonusHp, price, type, picture, nameColor);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "bonusDamage = " + bonusDamage + ", " +
                "bonusHp = " + bonusHp + ", " +
                "price = " + price + ", " +
                "type = " + type + ", " +
                "picture = " + picture + ", " +
                "nameColor = " + nameColor + ")";
    }
}
