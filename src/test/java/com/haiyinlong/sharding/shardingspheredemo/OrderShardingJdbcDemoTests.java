package com.haiyinlong.sharding.shardingspheredemo;

import com.haiyinlong.sharding.shardingspheredemo.entity.OrderEntity;
import com.haiyinlong.sharding.shardingspheredemo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderShardingJdbcDemoTests
 * @author HaiYinLong
 * @version 2025/10/14 17:57
**/
public class OrderShardingJdbcDemoTests extends ShardingsphereDemoApplicationTests{
    @Autowired
    private OrderRepository orderRepository;
    @Test
    void testSaveOrder() {
        OrderEntity orderEntity;
        for (int i = 0; i < 5; i++) {
            orderEntity = new OrderEntity();
            orderEntity.setUserId(1185230886530994177L);
            orderEntity.setOrderNumber("20251014"+i);
            orderEntity.setCreateTime(LocalDateTime.now());
            orderEntity.setUpdateTime(LocalDateTime.now());
            orderEntity.setStatus(0);
            orderRepository.save(orderEntity);
        }
    }

    @Test
    void testSaveHistoryUserIdOrder() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(1185182762139648L);
        orderEntity.setOrderNumber("20251014");
        orderEntity.setCreateTime(LocalDateTime.now());
        orderEntity.setUpdateTime(LocalDateTime.now());
        orderEntity.setStatus(0);
        orderRepository.save(orderEntity);
    }

    @Test
    void testGetHistoryUserIdOrder() {
        List<OrderEntity> userOrderList = orderRepository.findByUserId(1185182762139648L);
        System.out.println(userOrderList.size());
    }

    @Test
    void testGetUserIdOrderByBetweenUserId() {
        List<OrderEntity> userOrderList = orderRepository.findByUserIdBetween(1185182762139609L,1185182762139648L);
        System.out.println(userOrderList.size());

    }
}
