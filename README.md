# ShardingSphere 分库实战示例（ShardingsphereDemo）

> 基于 ShardingSphere-JDBC 实现用户数据按 `user_id` 水平分库，支持绑定表与广播表，适用于高并发场景下的数据库扩展。

![ShardingSphere](https://shardingsphere.apache.org/website/assets/img/shardingsphere.png)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-green)
![Java 17](https://img.shields.io/badge/Java-17-blue)
![ShardingSphere-JDBC-5.5.2](https://img.shields.io/badge/ShardingSphere--JDBC-5.5.2-orange)

---

## 📌 项目简介

本项目演示了如何使用 **Apache ShardingSphere-JDBC** 实现基于 `user_id` 的水平分库策略，确保用户及其订单数据存储在同一个数据库实例中，提升查询性能与数据一致性。

同时，通过配置 **广播表（Broadcast Table）**，实现如字典表 `sys_dic` 在所有分片库中自动同步，保证全局数据一致性。

> 可参考 `YamlRootConfiguration` class 文件配置yml，网上查到的或通义回答的经常是5.0版本以前的配置，不是最新的。

---

## 🚀 核心功能

- ✅ 基于 `user_id` 的分库策略（2 个库：`bus0`, `bus1`）
- ✅ 绑定表（Binding Table）：`user` 与 `order` 同库存储
- ✅ 广播表（Broadcast Table）：`sys_dic` 在所有库中自动复制
- ✅ 使用 Snowflake 算法生成分布式唯一主键
- ✅ 基于 YAML 配置，零代码侵入
- ✅ 集成 Spring Boot + JPA

---

## 🛠️ 技术栈

| 技术 | 版本 |
|------|------|
| Spring Boot | 3.2.3 |
| Java | 17 |
| ShardingSphere-JDBC | 5.5.2 |
| MySQL | 8.0+ |
| JPA/Hibernate | - |
| SnakeYAML | 2.2 |

---
## 📄 配置说明
### 1. `application.yml`
```yaml
spring:
  application:
    name: ShardingsphereDemo
  datasource:
    driver-class-name: org.apache.shardingsphere.driver.ShardingSphereDriver # 指定ShardingSphere数据源驱动
    url: jdbc:shardingsphere:classpath:shardingsphere-config.yml # 指定ShardingSphere数据源配置文件
```

### 2. `shardingsphere-config.yml`
```yaml
dataSources:  # 配置分库数据源
  bus0:
    #..
  bus1:
    #..
rules:
  - !BROADCAST # 广播表，默认所有库都复制
    tables:
      - sys_dic

  - !SHARDING # 分库和分表的配置
    tables:
      user:
        actualDataNodes: bus${0..1}.user
        databaseStrategy: # 表分库的配置
          standard:
            shardingColumn: user_id
            shardingAlgorithmName: db-inline
        keyGenerateStrategy: # 表主键生成策略（一般用雪花ID生成器）
          column: user_id
          keyGeneratorName: snowflake_generator
      order:
        actualDataNodes: bus${0..1}.order
        databaseStrategy: # 表分库的配置，该表不需要配置 `keyGenerateStrategy` 
          standard:
            shardingColumn: user_id
            shardingAlgorithmName: db-inline
    bindingTables: # 绑定表，确保用户及其订单数据存储在同一个数据库实例中
      - user,order
    shardingAlgorithms:
      db-inline:
        type: INLINE
        props:
          algorithm-expression: bus${user_id % 2}
    keyGenerators:
      snowflake_generator:
        type: SNOWFLAKE
        props:
          worker-id: 5
          data-center-id: 1

props:
  sql-show: true
  show-process-list-enabled: true
```
---
## 🔧 使用说明
### 数据库准备
1. 创建数据库 `bus0` 和 `bus1`
```sql
CREATE DATABASE `bus0` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE DATABASE `bus1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

```
2. 创建表 `user` 、 `order` 、 `sys_dic`; 注意两个库都要创建表
```sql
CREATE TABLE `order` (
                         `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                         `user_id` bigint unsigned DEFAULT NULL COMMENT '用户id',
                         `order_number` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单号',
                         `status` int unsigned DEFAULT NULL COMMENT '订单状态',
                         `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                         `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `sys_dic` (
                           `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                           `code` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编码',
                           `value` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '值',
                           `create_time` datetime DEFAULT NULL COMMENT '创建日期',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典表';

CREATE TABLE `user` (
                        `user_id` bigint unsigned NOT NULL COMMENT '用户id',
                        `account` bigint unsigned DEFAULT NULL COMMENT '登录账号',
                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `user_user_id_IDX` (`user_id`) USING BTREE,
                        UNIQUE KEY `user_account_IDX` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

```
### 运行项目
```shell
mvn spring-boot:run
```
---
## 📊 分库说明

| 数据     | 分片键   | 分片算法       | 存储位置               |
|----------|----------|----------------|------------------------|
| user     | user_id  | `user_id % 2`  | `bus0` 或 `bus1`       |
| order    | user_id  | `user_id % 2`  | 与 `user` 同库         |
| sys_dic  | -        | 广播表         | `bus0` 和 `bus1` 同时写入 |

✅ 优势：
* 相同 user_id 的 user 和 order 始终在同一个库，支持高效关联查询。
* 字典表变更会自动同步到所有节点。
---
## 🧩 旧库进行分库
自定义配置分库策略，并修改配置

```java
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

```

```yaml
rules:
  - !SHARDING
    tables:
      user:
        actualDataNodes: bus.user,bus${0..1}.user
        databaseStrategy:
          standard:
            shardingColumn: user_id
            shardingAlgorithmName: smart-db-router  # 指定自定义分库策略
      order:
        actualDataNodes: bus.order,bus${0..1}.order
        databaseStrategy:
          standard:
            shardingColumn: user_id
            shardingAlgorithmName: smart-db-router   # 指定自定义分库策略
    shardingAlgorithms:
      smart-db-router:  # 配置自定义分库策略
        type: CLASS_BASED
        props:
          strategy: STANDARD
          algorithmClassName: com.haiyinlong.sharding.shardingspheredemo.config.SmartUserShardingAlgorithm
      
```
```sql
 # 创建bus库, user_id表新增测试用户
INSERT INTO bus.`user` (user_id, account, create_time) VALUES(1185182762139648, 1, '2025-10-15 11:33:10');
```
---
## 💬 思考
1. 数据分库后，后台业务系统怎么进行分页数据查询?
>  a. 引入ES/ClickHouse/Doris 新增汇总宽表？会有延时，且宽表数据量会变大。     
>  b. 使用时间加游标的方式，获取最新数据。但是不能跨页查询。    
2. 如果用宽表或其他形式汇总数据提供查询，那更新要怎么做？
>  宽表用来分页查询，获取具体数据和更新的时候操作分库表。

先考虑读写分离是否满足要求（解决读很频繁的问题），在考虑“不分库 + 缓存 + ES” 这种简单高效。 只有当数据量 > 1亿、QPS > 5000 时，才真正需要分库分表。     
当分库分表维护成本过高时，可以尝试使用OceanBase。

