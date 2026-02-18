package com.example.warehouse.sensors;

import com.example.warehouse.config.SensorData;
import com.example.warehouse.strategy.SensorStrategy;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("Humidity")
@RequiredArgsConstructor
public class HumiditySensor implements SensorStrategy {

    private static final Logger logger = LoggerFactory.getLogger(HumiditySensor.class);
    private final SensorData props;

    @Override
    public void checkThreshold(String message) {
        try {
            message = message.trim();
            String[] parts = message.split(";");
            int value = Integer.parseInt(parts[1].split("=")[1]);
            if (value > props.getHumidity().getThreshold()) {
                logger.error("ALARM! Humidity exceeded: {}%", value);
            } else {
                logger.info("Humidity reading normal: {}%", value);
            }
        } catch (Exception e) {
            logger.error("Failed to parse humidity message: {}", message, e);
        }
    }
}
