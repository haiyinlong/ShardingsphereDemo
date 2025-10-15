package com.haiyinlong.sharding.shardingspheredemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * SysDicEntity
 * @author HaiYinLong
 * @version 2025/10/14 17:53
**/
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sys_dic")
public class SysDicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "`value`")
    private String value;
    @Column(name = "create_time")
    private LocalDateTime createTime;
}
