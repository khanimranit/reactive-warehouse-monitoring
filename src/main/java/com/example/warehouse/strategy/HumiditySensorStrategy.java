package com.example.warehouse.strategy;

import com.example.warehouse.config.SensorProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("Humidity")
@RequiredArgsConstructor
public class HumiditySensorStrategy implements SensorStrategy {

    private static final Logger logger = LoggerFactory.getLogger(HumiditySensorStrategy.class);
    private final SensorProperties props;

    @Override
    public void checkThreshold(String message) {
        try {
            String[] parts = message.split(";");
            int value = Integer.parseInt(parts[1].split("=")[1]);
            if (value > props.getHumidity().getThreshold()) {
                logger.warn("ALARM! Humidity exceeded: {}%", value);
            } else {
                logger.info("Humidity reading normal: {}%", value);
            }
        } catch (Exception e) {
            logger.error("Failed to parse humidity message: {}", message, e);
        }
    }
}
