package com.problemfighter.pfspring.hl2c;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.problemfighter.pfspring.hl2c.*"})
@EnableCaching
public class Hl2cScanner {

}
