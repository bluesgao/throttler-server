package com.bluesgao.throttlerserver.service.impl;

import com.bluesgao.throttlerserver.anno.RateLimit;
import com.bluesgao.throttlerserver.anno.RateLimitType;
import com.bluesgao.throttlerserver.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;

@Slf4j
@Service
public class RateLimitServiceImpl implements RateLimitService {
    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    private static RedisScript<Long> tpsLuaScript;
    private static RedisScript tokenLuaScript;

    static {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setLocation(new ClassPathResource("/lua/tps.lua"));
        redisScript.setResultType(Long.class);
        tpsLuaScript = redisScript;
    }

    private Boolean acquireToken(String key, RateLimit rateLimit) {
        Long ret = redisTemplate.execute(tpsLuaScript, Collections.singletonList(rateLimit.key()), rateLimit.count(), rateLimit.time());
        if (ret != null && ret.longValue() <= rateLimit.count()) {
            return true;
        }
        return false;
    }

    private Boolean acquireThreshold(String key, RateLimit rateLimit) {
        Long ret = redisTemplate.execute(tpsLuaScript, Collections.singletonList(rateLimit.key()), rateLimit.count(), rateLimit.time());
        if (ret != null && ret.longValue() <= rateLimit.count()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean throttle(String key, RateLimit rateLimit) {
        log.info("key:{},rateLimit:{}", key, rateLimit);
        if (rateLimit.type().equals(RateLimitType.THRESHOLD)) {
            return acquireThreshold(key, rateLimit);
        } else if (rateLimit.type().equals(RateLimitType.TOKEN)) {
            return acquireToken(key, rateLimit);
        }
        return false;
    }
}
