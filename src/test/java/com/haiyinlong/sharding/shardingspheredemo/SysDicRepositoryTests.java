package com.haiyinlong.sharding.shardingspheredemo;

import com.haiyinlong.sharding.shardingspheredemo.entity.SysDicEntity;
import com.haiyinlong.sharding.shardingspheredemo.repository.SysDicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * SysDicRepositoryTests
 * @author HaiYinLong
 * @version 2025/10/15 15:05
**/
public class SysDicRepositoryTests extends ShardingsphereDemoApplicationTests {
    @Autowired
    SysDicRepository sysDicRepository;
    @Test
    void testSaveSysDic() {
        SysDicEntity sysDicEntity = new SysDicEntity();
        sysDicEntity.setCode("local_city");
        sysDicEntity.setValue("shenzhen");
        sysDicEntity.setCreateTime(LocalDateTime.now());
        sysDicRepository.save(sysDicEntity);
    }
}
