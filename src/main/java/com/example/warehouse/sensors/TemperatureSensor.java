package com.example.warehouse.sensors;

import com.example.warehouse.config.SensorData;
import com.example.warehouse.strategy.SensorStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("Temperature")
@RequiredArgsConstructor
@Slf4j
public class TemperatureSensor implements SensorStrategy {

    private final SensorData props;

    @Override
    public void checkThreshold(String message) {
        try {
            message = message.trim();
            String[] parts = message.split(";");
            int value = Integer.parseInt(parts[1].split("=")[1]);
            if (value > props.getTemperature().getThreshold()) {
                log.error("ALARM! Temperature exceeded: {}°C", value);
            } else {
                log.info("Temperature reading normal: {}°C", value);
            }
        } catch (Exception e) {
            log.error("Failed to parse temperature message: {}", message, e);
        }
    }
}
