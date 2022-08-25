package com.mirceanealcos.confruntarea.entity;

import javax.persistence.*;

/**
 * Monster entity from the DB
 * Represents enemies that users can fight using champions and items
 * Relationships: none
 */
@Entity
@Table(name = "monster")
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer level;
    private Integer hp;

    @Column(name = "base_damage")
    private Integer baseDamage;

    @Column(name = "money_reward")
    private Integer moneyReward;

    @Column(name = "exp_reward")
    private Integer expReward;
    private String picture;

    @Column(name = "name_color")
    private String nameColor;

    public Monster() {

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

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", hp=" + hp +
                ", baseDamage=" + baseDamage +
                ", moneyReward=" + moneyReward +
                ", expReward=" + expReward +
                ", picture=" + picture +
                ", nameColor=" + nameColor +
                '}';
    }

}
