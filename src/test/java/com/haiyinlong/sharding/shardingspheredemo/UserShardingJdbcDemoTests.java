package com.haiyinlong.sharding.shardingspheredemo;

import com.haiyinlong.sharding.shardingspheredemo.entity.UserEntity;
import com.haiyinlong.sharding.shardingspheredemo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * UserShardingJdbcDemoTests
 * @author HaiYinLong
 * @version 2025/10/14 18:28
**/
public class UserShardingJdbcDemoTests extends ShardingsphereDemoApplicationTests{
    @Autowired
    UserRepository userRepository ;
    @Test
    void testSaveUser() {
        UserEntity userEntity;
        long l = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            userEntity = new UserEntity();
            userEntity.setAccount(String.valueOf(l+i));
            userEntity.setCreateTime(LocalDateTime.now());
            userRepository.save(userEntity);
            parseSnowflakeId(userEntity.getUserId());
        }
    }
    public void parseSnowflakeId(Long id) {
        // Snowflake 结构（ShardingSphere 默认）：
        // 1-bit sign | 41-bit timestamp | 10-bit workerId | 12-bit sequence

        final long sequenceBits = 12L;
        final long workerIdBits = 10L;
        final long datacenterIdBits = 0L; // ShardingSphere 默认不启用 datacenter-id

        final long maxWorkerId = ~(-1L << workerIdBits); // 1023
        final long maxDatacenterId = ~(-1L << datacenterIdBits);

        final long sequenceMask = ~(-1L << sequenceBits);
        final long workerIdShift = sequenceBits;
        final long datacenterIdShift = sequenceBits + workerIdBits;
        final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

        // 开始解析
        long sequence = id & sequenceMask;
        long workerId = (id >>> workerIdShift) & maxWorkerId;
        long datacenterId = (id >>> datacenterIdShift) & maxDatacenterId;
        long timestamp = (id >>> timestampLeftShift) & (~(-1L << 41));

        // 基准时间（Snowflake 的纪元时间）
        long twepoch = 1288834974657L; // 2010-11-04 01:42:54.657 UTC
        long realTimestamp = twepoch + timestamp;

        System.out.println("ID: " + id);
        System.out.println("Timestamp: " + new java.util.Date(realTimestamp));
        System.out.println("Worker ID: " + workerId);           // 👈 看这里！
        System.out.println("Datacenter ID: " + datacenterId);
        System.out.println("Sequence: " + sequence);
    }
}
