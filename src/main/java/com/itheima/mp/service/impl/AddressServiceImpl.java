package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.mapper.AddressMapper;
import com.itheima.mp.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * @author craft
 * @date 2024/11/4 17:40
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
}
