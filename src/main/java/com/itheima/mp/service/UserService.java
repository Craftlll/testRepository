package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;

import java.util.List;

/**
 * @author craft
 * @date 2024/11/4 15:33
 */
public interface UserService extends IService<User> {
    /**
     * 根据id和money扣除balance
     *
     * @param id
     * @param money
     */
    void deductBalance(Long id, Integer money);

    /**
     * 根据id查询用户的信息和地址信息
     *
     * @param id
     * @return
     */
    UserVO queryUserAndAddressById(Long id);

    /**
     * 批量查询用户
     *
     * @param ids
     * @return
     */
    List<UserVO> queryUserAndAddressByIds(List<Long> ids);

    /**
     * 用户分页查询
     *
     * @param userQuery
     * @return
     */
    PageDTO<UserVO> userPageQuery(UserQuery userQuery);

    /**
     * 新增用户
     *
     * @param userFormDTO
     */
    void saveUser(UserFormDTO userFormDTO);

    /**
     * 更新用户
     *
     * @param userFormDTO
     */
    void updateUser(UserFormDTO userFormDTO);
}
