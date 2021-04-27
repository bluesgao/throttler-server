package com.bluesgao.throttlerserver.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    String key() default "";

    RateLimitType type() default RateLimitType.THRESHOLD;

    int time() default 0;

    int count() default 0;
}
