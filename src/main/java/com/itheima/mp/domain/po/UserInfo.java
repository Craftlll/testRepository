package com.itheima.mp.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author craft
 * @date 2024/11/5 16:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")  //静态构造
public class UserInfo {
    private Integer age;
    private String intro;
    private String gender;
}
