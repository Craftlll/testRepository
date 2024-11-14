package com.itheima.mp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author craft
 * @date 2024/11/5 16:23
 */
@Getter
public enum UserStatus {
    NORMAL(1,"正常"),
    FREEZE(2,"冻结");

    @EnumValue  //设置value是存储到数据库的值
    private final int value;
    @JsonValue  //此注解的作用是设置此枚举类型序列化后展示的字段
    private final String desc;

    UserStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
