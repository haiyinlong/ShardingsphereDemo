package com.haiyinlong.sharding.shardingspheredemo.repository;

import com.haiyinlong.sharding.shardingspheredemo.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderRepository
 * @author HaiYinLong
 * @version 2025/10/14 17:55
**/
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
