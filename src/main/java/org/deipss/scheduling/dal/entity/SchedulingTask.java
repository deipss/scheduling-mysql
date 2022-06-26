package org.deipss.scheduling.dal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.deipss.scheduling.enums.TaskStatusEnum;
import org.springframework.scheduling.annotation.Scheduled;

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

    @TableField("start")
    private Date start;

    @TableField("next_start")
    private Date nextStart;

    @TableField("end")
    private Date end;

    @TableField("timeout")
    private Date timeout;

    @TableField("version")
    private int version;

    @TableField("try_lock_cnt")
    private  int tryLockCnt;

    @TableField("tm_create")
    private Date tmCreate;

    @TableField("tm_modify")
    private Date tmModify;


}
