package com.wht.springcloud.fangchanwang.interceptor;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MailHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int errorCode = 0;

        if (errorCode != 0){
            return Health.down().withDetail("ErrorCode",errorCode).build();
        }

        return Health.up().build();
    }
}
