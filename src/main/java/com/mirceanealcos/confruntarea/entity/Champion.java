package com.mirceanealcos.confruntarea.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * The entity for champion in the DB
 * Represents the champions that users will use in order to fight monsters
 * Relationships: user (many-to-many), ability (one-to-many)
 */
@Entity
@Table(name = "champion")
public class Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer hp;

    @Column(name = "base_damage")
    private Integer baseDamage;
    private Integer price;
    private Integer mana;
    private String picture;
    @Column(name = "name_color")
    private String nameColor;

    @OneToMany(mappedBy = "champion", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Ability> abilities = new HashSet<>();

    @OneToMany(mappedBy = "champion", orphanRemoval = true)
    private Set<ChampionOwnership> ownerships = new HashSet<>();

    public Champion() {

    }

    public Long getId() {
        return id;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<Ability> abilities) {
        this.abilities = abilities;
    }

    public Set<ChampionOwnership> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(Set<ChampionOwnership> ownerships) {
        this.ownerships = ownerships;
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

    public Integer getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(Integer baseDamage) {
        this.baseDamage = baseDamage;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    @Override
    public String toString() {
        return "Champion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hp=" + hp +
                ", baseDamage=" + baseDamage +
                ", price=" + price +
                ", mana=" + mana +
                ", picture='" + picture + '\'' +
                ", nameColor='" + nameColor + '\'' +
                ", abilities=" + abilities +
                ", ownerships=" + ownerships +
                '}';
    }
}
