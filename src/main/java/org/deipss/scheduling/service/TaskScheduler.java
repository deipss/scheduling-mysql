package org.deipss.scheduling.service;

import java.util.List;

public interface TaskScheduler <T> {

    List<T> scan();
}
