package com.example.warehouse.strategy;

import com.example.warehouse.config.SensorData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("Temperature")
@RequiredArgsConstructor
public class TemperatureSensorStrategy implements SensorStrategy {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureSensorStrategy.class);
    private final SensorData props;

    @Override
    public void checkThreshold(String message) {
        try {
            message = message.trim();
            String[] parts = message.split(";");
            int value = Integer.parseInt(parts[1].split("=")[1]);
            if (value > props.getTemperature().getThreshold()) {
                logger.error("ALARM! Temperature exceeded: {}°C", value);
            } else {
                logger.info("Temperature reading normal: {}°C", value);
            }
        } catch (Exception e) {
            logger.error("Failed to parse temperature message: {}", message, e);
        }
    }
}
