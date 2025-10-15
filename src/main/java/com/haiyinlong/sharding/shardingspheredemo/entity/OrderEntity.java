package com.haiyinlong.sharding.shardingspheredemo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * OrderEntity
 * @author HaiYinLong
 * @version 2025/10/14 17:53
**/
@Data
@Entity
@Table(name = "`order`")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
