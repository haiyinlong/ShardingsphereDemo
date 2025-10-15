package com.haiyinlong.sharding.shardingspheredemo.repository;

import com.haiyinlong.sharding.shardingspheredemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 * @author HaiYinLong
 * @version 2025/10/14 18:28
**/
@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Long> {
}
