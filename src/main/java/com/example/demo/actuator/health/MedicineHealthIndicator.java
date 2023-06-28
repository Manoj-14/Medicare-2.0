package com.example.demo.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class MedicineHealthIndicator implements HealthIndicator {

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    @Override
    public Health health() {
        boolean inValid = Runtime.getRuntime().maxMemory() < (100 * 1024 * 1024);
        Status status = inValid ? Status.DOWN : Status.UP ;
        return Health.status(status).build();
    }
}
