package com.problemfighter.pfspring.module.redis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.problemfighter.pfspring.module.redis.*"})
@EnableCaching
public class RedisScanner {

}
