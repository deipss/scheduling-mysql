package org.deipss.scheduling.service;

import java.util.List;

public interface TaskScheduler<R> {

    void scheduling();

    List<Task<R>> scan();


    int init();


}
