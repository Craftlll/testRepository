package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

//@SpringBootTest
class UserMapperTest {

//    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setId(6L);
        user.setUsername("craft");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(4L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
    }

    @Test
    void testQueryWrapper(){
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id", "username", "info", "balance")
                .like("username", "o")
                .ge("balance", 1000);

        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

    @Test
    void testQueryUpdateWrapper(){
        User user = new User();
        user.setBalance(1600);
        user.setUsername("jack");

        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", "jack");

        userMapper.update(user,wrapper);
    }

    @Test
    void testUpdateWrapper(){
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id", 1, 2, 4);

        userMapper.update(wrapper);
    }

    @Test
    void testLambdaWrapper(){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getInfo, User::getBalance)
                .like(User::getUsername, "o")
                .ge(User::getBalance, 2000);

        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

    @Test
    void testCustomUpdateWrapper(){
        //1.更新条件
        int amount = 200;
        List<Integer> ids = List.of(1, 2, 4);
        //2.构建where条件
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<User>().in(User::getId, ids);
        //3.更新
        userMapper.updateBalanceByIds(wrapper, amount);
    }
}