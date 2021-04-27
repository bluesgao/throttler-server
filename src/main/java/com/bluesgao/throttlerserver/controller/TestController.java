package com.bluesgao.throttlerserver.controller;

import com.bluesgao.throttlerserver.anno.RateLimit;
import com.bluesgao.throttlerserver.anno.RateLimitType;
import com.bluesgao.throttlerserver.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/user/{name}")
    @RateLimit(key = "user", type = RateLimitType.THRESHOLD, time = 60, count = 1000)
    @ResponseBody
    public CommonResult<String> user(@PathVariable String name) {
        log.info("user:" + name);
        return CommonResult.success("hello:" + name);
    }
}
