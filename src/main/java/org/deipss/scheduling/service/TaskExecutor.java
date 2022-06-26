package org.deipss.scheduling.service;

public interface TaskExecutor<T, R> {

    R execute(T t);
}
