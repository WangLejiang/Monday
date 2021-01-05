package com.wanglejiang.common.enums;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public interface BaseEnum {
    /**
     * 将制定的枚举集合转成 map
     * key -> name
     * value -> desc
     *
     * @param list
     * @return
     */
    static Map<String, String> getMap(BaseEnum[] list) {
        Map<String, String> map = new LinkedHashMap<>(list.length);
        Arrays.asList(list).forEach(en -> map.put(en.getCode(), en.getDesc()));
        return map;
    }

    static Map<String, String> getMapByClassDef(String classFullName) {
        try {
            Method method = Class.forName(classFullName).getMethod("values");
            BaseEnum[] enums = (BaseEnum[]) method.invoke(null);
            return BaseEnum.getMap(enums);
        } catch (Exception e) {
            throw new InvalidParameterException("传入的枚举名无效");
        }
    }


    /**
     * 编码重写
     */
    default String getCode() {
        return toString();
    }

    /**
     * 描述
     */
    String getDesc();

    /**
     * 枚举值
     */
    @JsonIgnore
    default String getValue() {
        return getCode();
    }
}
