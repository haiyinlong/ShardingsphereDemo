package com.haiyinlong.sharding.shardingspheredemo.repository;

import com.haiyinlong.sharding.shardingspheredemo.entity.SysDicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SysDicRepository
 * @author HaiYinLong
 * @version 2025/10/15 15:04
**/
@Repository
public interface SysDicRepository extends JpaRepository<SysDicEntity, Long> {
}
