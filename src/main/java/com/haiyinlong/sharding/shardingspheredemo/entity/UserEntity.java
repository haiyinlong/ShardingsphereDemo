package com.haiyinlong.sharding.shardingspheredemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserEntity
 * @author HaiYinLong
 * @version 2025/10/14 17:53
**/
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "account")
    private String account;
    @Column(name = "create_time")
    private LocalDateTime createTime;
}
