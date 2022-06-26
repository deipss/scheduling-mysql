package org.deipss.scheduling.service;

public interface TaskLock {

    boolean lock();

    boolean unlock();

    boolean tryLock(int cnt);

    boolean tryUnlock(int cnt);

}
