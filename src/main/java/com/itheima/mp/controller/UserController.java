package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author craft
 * @date 2024/11/4 15:50
 */
@RestController
@Slf4j
@Api(tags = "用户接口")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ApiOperation("新增用户")
    public void saveUser(UserFormDTO userFormDTO) {
        log.info("新增用户:{}", userFormDTO);
        userService.saveUser(userFormDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public void deleteById(@PathVariable Long id) {
        log.info("删除用户:{}", id);
        userService.removeById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询用户")
    public UserVO getById(@PathVariable Long id) {
        log.info("查询用户:{}", id);

        UserVO userVO = userService.queryUserAndAddressById(id);
        return userVO;
    }

    @GetMapping
    @ApiOperation("根据id批量查询")
    public List<UserVO> getByIds(@RequestParam("ids") List<Long> ids) {
        log.info("批量查询用户:{}", ids);

        List<UserVO> userVOS = userService.queryUserAndAddressByIds(ids);

        return userVOS;
    }

    @PutMapping("{id}/deduction/{money}")
    @ApiOperation("扣减用户余额")
    public void deductBalance(@PathVariable("id") Long id, @PathVariable("money") Integer money) {
        userService.deductBalance(id, money);
    }

    @GetMapping("/list")
    @ApiOperation("根据id集合查询用户（条件查询）")
    public List<UserVO> queryUsers(UserQuery userQuery) {
        //1.组织条件查询条件
        String userName = userQuery.getName();
        Integer status = userQuery.getStatus();
        Integer maxBalance = userQuery.getMaxBalance();
        Integer minBalance = userQuery.getMinBalance();

        //2.查询用户
        List<User> users = userService.lambdaQuery()
                .eq(userName != null, User::getUsername, userName)
                .eq(status != null, User::getStatus, status)
                .ge(maxBalance != null, User::getBalance, maxBalance)
                .le(minBalance != null, User::getBalance, minBalance)
                .list();

        return BeanUtil.copyToList(users, UserVO.class);
    }

    @GetMapping("/page")
    @ApiOperation("用户分页查询")
    public PageDTO<UserVO> userPageQuery(UserQuery userQuery){
        log.info("用户分页查询:{}",userQuery);
        PageDTO<UserVO> userVOS = userService.userPageQuery(userQuery);
        return userVOS;
    }

    @PutMapping
    @ApiOperation("更新用户")
    public void updateUser(UserFormDTO userFormDTO){
        log.info("更新用户:{}",userFormDTO);
        userService.updateUser(userFormDTO);
    }
}
