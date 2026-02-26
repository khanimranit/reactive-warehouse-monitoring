package com.example.warehouse.sensors;

import com.example.warehouse.config.SensorData;
import com.example.warehouse.strategy.SensorStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("location")
@RequiredArgsConstructor
@Slf4j
public class LocationSensor implements SensorStrategy {

    private final SensorData props;

    @Override
    public void checkThreshold(String message) {
        log.info("Received location message: {}", message);
        try {
            message = message.trim();
            String[] parts = message.split(";");
            int value = Integer.parseInt(parts[1].split("=")[1]);
            if (value > props.getTemperature().getThreshold()) {
                log.error("ALARM! location exceeded: {}", value);
            } else {
                log.info("location reading normal: {}", value);
            }
        } catch (Exception e) {
            log.error("Failed to parse location message: {}", message, e);
        }
    }
}
