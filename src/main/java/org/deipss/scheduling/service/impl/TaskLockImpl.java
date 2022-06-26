package org.deipss.scheduling.service.impl;

import org.deipss.scheduling.service.TaskLock;

public class TaskLockImpl implements TaskLock {


    @Override
    public boolean lock(String lock) {
        return false;
    }

    @Override
    public boolean unlock(String lock) {
        return false;
    }

    @Override
    public boolean tryLock(int cnt, String lock) {
        return false;
    }

    @Override
    public boolean tryUnlock(int cnt, String lock) {
        return false;
    }
}
