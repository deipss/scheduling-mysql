package org.deipss.scheduling.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author hxl
 * @version 1.0
 * @since 2022/1/28 10:39
 */
@Slf4j
public class SleepUtil {

    public static void sleepMilliSecond(int origin, int bound) {
        try {
            long l = ThreadLocalRandom.current().nextLong(origin, bound);
            TimeUnit.MILLISECONDS.sleep(l);
        } catch (InterruptedException e) {
            log.error("休眠失败");
        }
    }


    public static void sleepMilliSecond(int c) {
        try {
            TimeUnit.MILLISECONDS.sleep(c);
        } catch (InterruptedException e) {
            log.error("休眠失败");
        }
    }

    public static void sleepSecond(int origin, int bound) {
        try {
            long l = ThreadLocalRandom.current().nextLong(origin, bound);
            TimeUnit.SECONDS.sleep(l);
        } catch (InterruptedException e) {
            log.error("休眠失败");
        }
    }


    public static void sleepSecond(int c) {
        try {
            TimeUnit.SECONDS.sleep(c);
        } catch (InterruptedException e) {
            log.error("休眠失败");
        }
    }


    public static void sleepMinutes(int origin, int bound) {
        try {
            long l = ThreadLocalRandom.current().nextLong(origin, bound);
            TimeUnit.MINUTES.sleep(l);
        } catch (InterruptedException e) {
            log.error("休眠失败");
        }
    }


    public static void sleepMinutes(int c) {
        try {
            TimeUnit.MINUTES.sleep(c);
        } catch (InterruptedException e) {
            log.error("休眠失败");
        }
    }
}
