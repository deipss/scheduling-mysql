package org.deipss.scheduling.dal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("scheduling_task")
public class SchedulingTask {

    @TableId("id")
    private Long id;

}
