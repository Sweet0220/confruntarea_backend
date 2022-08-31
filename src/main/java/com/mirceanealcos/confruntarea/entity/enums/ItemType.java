package com.mirceanealcos.confruntarea.entity.enums;

public enum ItemType {
    WEAPON("W"), POTION("P"), ARMOR("A"), THROWABLE("T");

    private String code;

    ItemType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
