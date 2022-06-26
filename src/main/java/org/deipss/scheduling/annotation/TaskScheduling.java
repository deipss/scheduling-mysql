package org.deipss.scheduling.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalTime;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@interface TaskScheduling {

    String name() default "任务";

    String start() default "00:00:00";

    String end() default "23:59:59";

    String interval() default "30S";




}