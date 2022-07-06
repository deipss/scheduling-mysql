package org.deipss.scheduling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatusEnum {
    LOCKED("LOCKED","已上锁"),

    UNLOCK("UNLOCK","未上锁"),
    LOCK_FAIL("LOCK_FAIL","上锁失败"),
    DOWN("DOWN","执行完成"),
    EXCEPTION("EXCEPTION","执行异常"),

    ;
    private String code;
    private String name;

}
