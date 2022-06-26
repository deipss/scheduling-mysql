package org.deipss.scheduling.service;

public interface TaskLock {

    boolean lock(String lock);

    boolean unlock(String lock);

    boolean tryLock(int cnt,String lock);

    boolean tryUnlock(int cnt,String lock);

}
