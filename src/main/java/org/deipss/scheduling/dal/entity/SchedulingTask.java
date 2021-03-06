package org.deipss.scheduling.dal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.deipss.scheduling.enums.TaskStatusEnum;

import java.time.LocalTime;
import java.util.Date;

@Data
@TableName("scheduling_task")
public class SchedulingTask {


    @TableId("id")
    private Long id;

    @TableField("lock_name")
    private String lockName;

    @TableField("task_status")
    private TaskStatusEnum taskStatus;

    @TableField("start_time")
    private LocalTime startTime;


    @TableField("end_time")
    private LocalTime endTime;

    @TableField("next_start")
    private Date nextStart;


    @TableField("try_lock_cnt")
    private int tryLockCnt;

    @TableField("time_gap")
    private String timeGap;

    @TableField("owner_ip")
    private String ownerIp;

    @TableField("tm_create")
    private Date tmCreate;

    @TableField("tm_modify")
    private Date tmModify;




}
