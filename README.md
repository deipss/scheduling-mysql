# 使用mysql实现定时任务的执行
- 多机唯一性：一个任务只能被一台机器执行
# sql
```mysql
DROP TABLE IF EXISTS `scheduling_task`;
CREATE TABLE `scheduling_task`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `lock_name` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '锁名称，为类全路径名',
  `task_status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '锁状态',
  `start_time` time(3) NULL DEFAULT NULL COMMENT '任务开始时间',
  `end_time` time(3) NULL DEFAULT NULL COMMENT '任务结束时间',
  `next_start` datetime(3) NULL DEFAULT NULL COMMENT '下次启动时间',
  `try_lock_cnt` int(0) NULL DEFAULT NULL COMMENT '上锁次数',
  `time_gap` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务执行间隔',
    `owner_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tm_create` datetime NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `tm_modify` datetime NULL DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `scheduling_task_history`;
CREATE TABLE `scheduling_task_history`  (
  `id` bigint(0) NOT NULL COMMENT '主键',
  `lock_name` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '锁名称，为类全路径名',
  `task_status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '锁状态',
  `start_time` time(3) NULL DEFAULT NULL COMMENT '任务开始时间',
  `end_time` time(3) NULL DEFAULT NULL COMMENT '任务结束时间',
  `next_start` datetime(3) NULL DEFAULT NULL COMMENT '下次启动时间',
  `try_lock_cnt` int(0) NULL DEFAULT NULL COMMENT '上锁次数',
  `time_gap` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务执行间隔',
    `owner_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tm_create` datetime NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `tm_modify` datetime NULL DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```

# 版本
- mysql 1.8
# 注意点
- application.property文件配置
```shell
scheduling.mysql.enabled=true
scheduling.mysql.url=jdbc:mysql://localhost:3306/testdb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false
scheduling.mysql.username=***
scheduling.mysql.password=***
```
- scheduling_task 表中lock_name 为类全路径名
# 新建任务
- 在scheduling_task表中新建一条记录
- 实现org.deipss.scheduling.service.AbstractTask类，重写doBiz()方法，需要将此类注入到spring容器中
```java
@Slf4j
@Component // 使用spring进行生命周期管理
public class TaskA extends AbstractTask {
    @Override
    public Boolean doBiz() {
        SleepUtil.sleepSecond(3);
        log.info("任务运行A");
        return true;
    }
}

```