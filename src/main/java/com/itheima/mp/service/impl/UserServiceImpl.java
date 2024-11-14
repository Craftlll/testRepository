package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author craft
 * @date 2024/11/4 15:36
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    @Transactional
    public void deductBalance(Long id, Integer money) {
        //1.查询用户
        User user = getById(id);
        //2.判断
        if (user == null) {
            throw new RuntimeException("用户id不存在");
        }
        if (user.getStatus() == UserStatus.FREEZE) {
            throw new RuntimeException("用户状态异常");
        }
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足");
        }

        //3.更新余额
        int remainBalance = user.getBalance() - money;

        lambdaUpdate()
                .set(User::getBalance, remainBalance)
                .set(remainBalance == 0, User::getStatus, 2)
                .eq(User::getId, id)
                .eq(User::getBalance, user.getBalance()) //乐观锁
                .update();
    }

    @Override
    public UserVO queryUserAndAddressById(Long id) {
        //1.查询用户基础信息
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        //2.查询地址信息
        List<Address> addressList = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, id)
                .list();

        //3.返回信息
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        List<AddressVO> addressVOS = BeanUtil.copyToList(addressList, AddressVO.class);
        userVO.setAddresses(addressVOS);

        return userVO;
    }

    @Override
    public List<UserVO> queryUserAndAddressByIds(List<Long> ids) {
        //1.查询用户
        List<User> users = listByIds(ids);
        if (CollUtil.isEmpty(users)){
            return Collections.emptyList();
        }

        //2.查询地址
        List<UserVO> userVOS = BeanUtil.copyToList(users, UserVO.class);
        userVOS.forEach(userVO -> {
            //静态工具查询
            List<Address> addressList = Db.lambdaQuery(Address.class)
                    .eq(Address::getUserId, userVO.getId())
                    .list();
            //设置地址
            userVO.setAddresses(BeanUtil.copyToList(addressList, AddressVO.class));
        });

        return userVOS;
    }

    /**
     * 分页查询简化版本
     *
     * @param userQuery
     * @return
     */
    @Override
    public PageDTO<UserVO> userPageQuery(UserQuery userQuery) {
        // page转换
        Page<User> mpPage = userQuery.toMpPage();

        // 查询
        String userName = userQuery.getName();
        Integer status = userQuery.getStatus();
        Page<User> userPage = lambdaQuery().like(userName != null, User::getUsername, userName)
                .eq(status != null, User::getStatus, status)
                .page(mpPage);

        //返回
        return PageDTO.of(userPage, UserVO.class);
    }

    /**
     * 新增用户
     *
     * @param userFormDTO
     */
    @Override
    public void saveUser(UserFormDTO userFormDTO) {
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        save(user);
    }

    /**
     * 更新用户
     *
     * @param userFormDTO
     */
    @Override
    public void updateUser(UserFormDTO userFormDTO) {
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        updateById(user);
    }
}
