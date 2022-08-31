package com.mirceanealcos.confruntarea.entity.enums;

public enum AbilityType {
    HEALING("H"), DAMAGE("D"), LIFESTEAL("L"), MANAREGEN("M");

    private String code;

    private AbilityType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
