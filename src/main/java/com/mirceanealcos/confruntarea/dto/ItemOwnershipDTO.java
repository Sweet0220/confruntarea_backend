package com.mirceanealcos.confruntarea.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mirceanealcos.confruntarea.entity.Item;
import com.mirceanealcos.confruntarea.entity.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * Link table (user-item) DTO  used for better data transfer between backend and frontend
 */
public class ItemOwnershipDTO implements Serializable {
    private Long id;
    private Integer itemCount;
    private Item item;
    private User user;

    public ItemOwnershipDTO() {
    }

    public ItemOwnershipDTO(Long id, Integer itemCount, Item item, User user) {
        this.id = id;
        this.itemCount = itemCount;
        this.item = item;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemOwnershipDTO entity = (ItemOwnershipDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.itemCount, entity.itemCount) &&
                Objects.equals(this.item, entity.item) &&
                Objects.equals(this.user, entity.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemCount, item, user);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "itemCount = " + itemCount + ", " +
                "item = " + item + ", " +
                "user = " + user + ")";
    }
}
