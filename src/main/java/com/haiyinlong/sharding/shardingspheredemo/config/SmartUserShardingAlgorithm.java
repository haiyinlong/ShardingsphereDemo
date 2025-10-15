package com.haiyinlong.sharding.shardingspheredemo.config;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * SmartUserShardingAlgorithm
 * @author HaiYinLong
 * @version 2025/10/15 17:28
**/
public class SmartUserShardingAlgorithm implements StandardShardingAlgorithm<Long> {
    private static final Long HISTORY_USER_ID = 1185182762139648L;
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long currentUserId = shardingValue.getValue();
        if(currentUserId <= HISTORY_USER_ID){
            return shardingValue.getDataNodeInfo().getPrefix();
        }
        return shardingValue.getDataNodeInfo().getPrefix()+(currentUserId % 2);
    }

    @Override
    public Collection<String> doSharding(
            Collection<String> availableTargetNames,
            RangeShardingValue<Long> shardingValue) {

        // 获取范围区间
        Long lower = shardingValue.getValueRange().hasLowerBound() ? shardingValue.getValueRange().lowerEndpoint() : null;
        Long upper = shardingValue.getValueRange().hasUpperBound() ? shardingValue.getValueRange().upperEndpoint() : null;

        Set<String> result = new HashSet<>();

        // 如果没有范围（如 null），退化为全库扫描（可选）
        if (lower == null && upper == null) {
            result.add("bus");
            result.add("bus0");
            result.add("bus1");
            result.retainAll(availableTargetNames);
            return result;
        }

        // 情况1：整个范围都在历史用户内（<= HISTORY_USER_ID）
        if (upper != null && upper <= HISTORY_USER_ID) {
            if (availableTargetNames.contains("bus")) {
                result.add("bus");
            }
        }
        // 情况2：整个范围都在新用户内（> HISTORY_USER_ID）
        else if (lower != null && lower > HISTORY_USER_ID) {
            if (lower % 2 == 0) result.add("bus0");
            if ((lower + 1) % 2 == 0 || (upper != null && upper >= lower + 1)) {
                result.add("bus1");
            }
            result.retainAll(availableTargetNames);
        }
        // 情况3：范围跨越了 HISTORY_USER_ID（最常见）
        else {
            // 必须查询 bus（历史数据）
            if (availableTargetNames.contains("bus")) {
                result.add("bus");
            }
            // 也必须查询 bus0 和 bus1（新数据）
            if (availableTargetNames.contains("bus0")) {
                result.add("bus0");
            }
            if (availableTargetNames.contains("bus1")) {
                result.add("bus1");
            }
        }

        return result;
    }
}
