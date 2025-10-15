package com.haiyinlong.sharding.shardingspheredemo;

import com.haiyinlong.sharding.shardingspheredemo.entity.OrderEntity;
import com.haiyinlong.sharding.shardingspheredemo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

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
}
