package com.mirceanealcos.confruntarea.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


/**
 * The entity for abilities in the DB
 * Represents powers that champions have to better fight monsters
 * Relationships: champions (many-to-one)
 */
@Entity
@Table(name = "ability")
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Integer healing;
    private Integer damage;
    private String picture;

    @Column(name = "mana_cost")
    private Integer manaCost;

    @ManyToOne
    @JoinColumn(name = "ref_champion")
    @JsonIgnore
    private Champion champion;

    public Ability() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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


    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public Integer getManaCost() {
        return manaCost;
    }

    public void setManaCost(Integer manaCost) {
        this.manaCost = manaCost;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", healing=" + healing +
                ", damage=" + damage +
                ", picture='" + picture + '\'' +
                ", mana_cost=" + manaCost +
                ", champion=" + champion +
                '}';
    }

}
