package com.haiyinlong.sharding.shardingspheredemo.controller;

import com.haiyinlong.sharding.shardingspheredemo.entity.OrderEntity;
import com.haiyinlong.sharding.shardingspheredemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DemoController
 * @author HaiYinLong
 * @version 2025/10/22 17:45
**/
@RequestMapping("/demo")
@RestController
public class DemoController {
    private final OrderRepository orderRepository;
    private static Long count = 0L;


    @Value("${app.env:default}")
    private String env;

    public DemoController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RequestMapping("/get")
    public String get() {
        count++;
        return env+": "+  count;
    }

    @RequestMapping("/order")
    public String order(Long userId) {
        List<OrderEntity> entityList = orderRepository.findByUserId(userId);
        return "size: "+ entityList.size() + ", userId:"+ userId;
    }
}
