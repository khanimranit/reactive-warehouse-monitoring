package com.example.warehouse.sensors;

import com.example.warehouse.config.SensorData;
import com.example.warehouse.strategy.SensorStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("Humidity")
@RequiredArgsConstructor
@Slf4j
public class HumiditySensor implements SensorStrategy {
    private final SensorData props;

    @Override
    public void checkThreshold(String message) {
        try {
            message = message.trim();
            String[] parts = message.split(";");
            int value = Integer.parseInt(parts[1].split("=")[1]);
            if (value > props.getHumidity().getThreshold()) {
                log.error("ALARM! Humidity exceeded: {}%", value);
            } else {
                log.info("Humidity reading normal: {}%", value);
            }
        } catch (Exception e) {
            log.error("Failed to parse humidity message: {}", message, e);
        }
    }
}
