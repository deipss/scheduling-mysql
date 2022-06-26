package org.deipss.scheduling.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.deipss.scheduling.dal.entity.SchedulingTask;

import java.util.List;


public interface SchedulingTaskMapper extends BaseMapper<SchedulingTask> {

    @Update("update scheduling_task set owner_ip = #{ownerIp}, try_lock_cnt=try_lock_cnt+1 " +
            "where lockName=#{lockName} and task_status ='UNLOCK' and owner_ip in( #{ownerIp},'0.0.0.0')")
    int lock(@Param("lockName") String lockName, @Param("ownerIp") String ownerIp);

    @Update("update scheduling_task set owner_ip = '0.0.0.0', try_lock_cnt=0 " +
            "where lockName=#{lockName} and task_status ='LOCK'  and owner_ip = #{ownerIp}")
    int unlock(@Param("lockName") String lockName, @Param("ownerIp") String ownerIp);


    List<String> scan();
}