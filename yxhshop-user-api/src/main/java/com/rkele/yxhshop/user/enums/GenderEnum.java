package com.rkele.yxhshop.user.enums;

/**
 * @author zhs@outlook.com
 */

public enum GenderEnum {
    UNKNOWN("未知"),
    MALE("男"),
    FEMALE("女");

    private String name;

    GenderEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
