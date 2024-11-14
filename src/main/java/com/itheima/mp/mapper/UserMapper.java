package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    void updateBalanceByIds(@Param(value = Constants.WRAPPER) LambdaUpdateWrapper<User> wrapper, int amount);
}
