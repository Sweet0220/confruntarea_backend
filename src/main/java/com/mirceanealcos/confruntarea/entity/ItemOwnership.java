package com.mirceanealcos.confruntarea.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Represents an entity for the link table between the users and the items
 */
@Entity
@Table(name = "item_ownership")
public class ItemOwnership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_count")
    private Integer itemCount;

    @ManyToOne
    @JoinColumn(name = "ref_item")
    @JsonIgnore
    private Item item;

    @ManyToOne
    @JoinColumn(name = "ref_user")
    @JsonIgnore
    private User user;

    public ItemOwnership() {

    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public String toString() {
        return "ItemOwnership{" +
                "id=" + id +
                ", itemCount=" + itemCount +
                ", item=" + item +
                ", user=" + user +
                '}';
    }
}
