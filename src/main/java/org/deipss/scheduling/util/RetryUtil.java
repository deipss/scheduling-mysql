package org.deipss.scheduling.util;


import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author hxl
 * @version 1.0
 * @since 2022/2/23 10:50
 */
@Slf4j
public class RetryUtil {

    public static int RETRY_NUM = 2;

    public static Boolean retry(RetryReturnBoolean retryReturnBoolean, int num) {
        for (int i = 0; i < num; i++) {
            try {
                if (retryReturnBoolean.run()) {
                    return true;
                }else{
                    log.info("RetryUtil 第{}次执行失败",i);
                }
            } catch (Exception e) {
                log.error("RetryUtil retry. errorMsg={}", e.getMessage(), e);
            }
        }
        return false;
    }

    public static <T> T retry(RetryReturnData<T> retryReturnData, int num) {
        for (int i = 0; i < num; i++) {
            try {
                T run = retryReturnData.run();
                if (Objects.nonNull(run)) {
                    return run;
                }else{
                    log.info("RetryUtil 第{}次执行失败",i);
                }
            } catch (Exception e) {
                log.error("RetryUtil retry. errorMsg={}", e.getMessage(), e);
            }
        }
        return null;
    }

    public static Boolean retry(RetryReturnBoolean retryReturnBoolean) {
        return retry(retryReturnBoolean, 3);
    }
    public static <T> T  retry(RetryReturnData<T> retryReturnData) {
        return retry(retryReturnData, 3);
    }

    public interface RetryReturnBoolean {
        Boolean run();
    }


    public interface RetryReturnData<T> {
        T run();
    }



    /**
     * 会返回结果的重试，重试2次
     * 返回null或者抛出异常都会重试
     *
     * @param t
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R retry(T t, Function<T, R> function) {
        R result = null;
        for (int i = 0; i < RETRY_NUM; i++) {
            try {
                R r = function.apply(t);
                if (r != null) {
                    result = r;
                    break;
                }
            } catch (Exception ex) {
                log.error("RetryUtil retry. times={}, error={}", (i + 1), ex.getMessage(), ex);
            }
        }
        return result;
    }
}