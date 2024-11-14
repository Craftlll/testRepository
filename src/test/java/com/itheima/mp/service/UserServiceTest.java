package com.itheima.mp.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author craft
 * @date 2024/11/4 15:37
 */
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testSave(){
        User user = new User();
        user.setUsername("LiChuang_5");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo(UserInfo.of(24,"计算机老师","男"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);
    }

    @Test
    void testDbGet(){
        User user = Db.getById(2L, User.class);
        System.out.println(user);
    }

    @Test
    void testDbList(){
        //复杂查询
        List<User> users = Db.lambdaQuery(User.class)
                .like(User::getUsername, "c")
                .le(User::getBalance, 400)
                .list();

        users.forEach(System.out::println);
    }

    @Test
    void testDbUpdate(){
        //Db更新操作
        Db.lambdaUpdate(User.class)
                .eq(User::getUsername, "craft")
                .set(User::getBalance, 100000)
                .update();

    }

    @Test
    void testEnum(){
        List<User> users = userService.list();
        users.forEach(System.out::println);
    }

    @Test
    void testPageQuery(){
        // 1.查询参数
        int pageNo = 1;
        int pageSize = 5;
        // 2.分页查询
        Page<User> page = Page.of(pageNo, pageSize);
        //添加排序顺序
        page.addOrder(OrderItem.desc("balance"));
        userService.page(page);
        // 3.总条数
        System.out.println("total="+page.getTotal());
        // 4.总页数
        System.out.println("pages="+page.getPages());
        // 5.数据
        List<User> records = page.getRecords();
        records.forEach(System.out::println);
    }
}