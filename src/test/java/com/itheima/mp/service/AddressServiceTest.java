package com.itheima.mp.service;

import com.itheima.mp.domain.po.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author craft
 * @date 2024/11/4 17:41
 */
@SpringBootTest
class AddressServiceTest {
    @Autowired
    private AddressService addressService;

    @Test
    void testLogicDelete() {
        addressService.removeById(59L);
    }

    @Test
    void testQuery() {
        List<Address> addressList = addressService.lambdaQuery()
                .eq(Address::getUserId, 2)
                .list();

        addressList.forEach(System.out::println);
    }
}