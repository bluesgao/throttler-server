package com.bluesgao.throttlerserver.service;

import com.bluesgao.throttlerserver.anno.RateLimit;

public interface RateLimitService {
    Boolean throttle(String key, RateLimit rateLimit);
}
