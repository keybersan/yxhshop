package com.rkele.yxhshop.goods.enums;

/**
 * @author zhs@outlook.com
 */
public enum CategoryLevelEnum {

    L1("一级类目"), L2("二级类目");

    CategoryLevelEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
