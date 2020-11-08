package com.problemfighter.pfspring.jdt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.problemfighter.pfspring.jdt.*"})
@EnableCaching
public class JdtScanner {

}
