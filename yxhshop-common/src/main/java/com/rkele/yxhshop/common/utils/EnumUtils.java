package com.rkele.yxhshop.common.utils;

/**
 * @author zhs
 */
public class EnumUtils {

    public static <E extends Enum<E>> E getEnum(Class<E> eClass, int index) {
        E[] enumConstants = eClass.getEnumConstants();
        for (E enumConstant : enumConstants) {
            if (enumConstant.ordinal() == index) {
                return enumConstant;
            }
        }
        return null;
    }

}
