package com.mirceanealcos.confruntarea.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirceanealcos.confruntarea.entity.enums.ItemType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Item entity from the DB
 * Represents the items that users will use to fight monsters
 * Relationships: users (many-to-many)
 */
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "bonus_damage")
    private Integer bonusDamage;

    @Column(name = "bonus_hp")
    private Integer bonusHp;
    private Integer price;
    private ItemType type;
    private String picture;

    @Column(name = "name_color")
    private String nameColor;

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    @JsonIgnore
    private Set<ItemOwnership> ownerships = new HashSet<>();

    public Item() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public Set<ItemOwnership> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(Set<ItemOwnership> ownerships) {
        this.ownerships = ownerships;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bonusDamage=" + bonusDamage +
                ", bonusHp=" + bonusHp +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", picture='" + picture + '\'' +
                ", nameColor='" + nameColor + '\'' +
                ", ownerships=" + ownerships +
                '}';
    }
}
