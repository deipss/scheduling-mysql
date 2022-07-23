package org.deipss.scheduling.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.deipss.scheduling.dal.entity.SchedulingTask;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;


public interface SchedulingTaskMapper extends BaseMapper<SchedulingTask> {

    @Update("update scheduling_task set owner_ip = #{ownerIp}, try_lock_cnt=try_lock_cnt+1 , task_status ='LOCKED' " +
            "where lock_name=#{lockName} and task_status ='UNLOCK' and owner_ip in( #{ownerIp},'0.0.0.0')" +
            "and #{nowTime} between start_time and end_time and #{nowDateTime} > next_start")
    int lock(@Param("lockName") String lockName, @Param("ownerIp") String ownerIp, @Param("nowTime") LocalTime nowTime, @Param("nowDateTime") Date nowDateTime);

    @Update("update scheduling_task set owner_ip = '0.0.0.0', try_lock_cnt=0, next_start=#{nextTime}, task_status ='UNLOCK'  " +
            "where lock_name=#{lockName} and task_status ='LOCKED'  and owner_ip = #{ownerIp}")
    int unlock(@Param("lockName") String lockName, @Param("ownerIp") String ownerIp, @Param("nextTime") Date nextTime);


    @Select("select lock_name from scheduling_task where #{nowTime} between start_time and end_time  and task_status ='UNLOCK' and #{nowDateTime}  > next_start")
    List<String> scanLockName(@Param("nowTime") LocalTime nowTime, @Param("nowDateTime") Date nowDateTime);

    @Select("select time_gap from scheduling_task where lock_Name =  #{lockName} ")
    String selectTimeGap(@Param("lockName") String lockName);

    @Select("select * from scheduling_task where lock_Name =  #{lockName} ")
    SchedulingTask selectByLockName(@Param("lockName") String lockName);

    @Select("select min(next_start) from scheduling_task ")
    Date selectMinNextStart();

    @Update("update scheduling_task set owner_ip = '0.0.0.0', try_lock_cnt=0, task_status ='UNLOCK' ,next_start = CURRENT_TIMESTAMP")
    int init();
}