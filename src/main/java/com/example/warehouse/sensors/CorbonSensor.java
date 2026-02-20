package com.example.warehouse.sensors;

import com.example.warehouse.config.SensorData;
import com.example.warehouse.strategy.SensorStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("Corbon")
@RequiredArgsConstructor
@Slf4j
public class CorbonSensor implements SensorStrategy {

    private final SensorData props;

    @Override
    public void checkThreshold(String message) {
        try {
            message = message.trim();
            String[] parts = message.split(";");
            int value = Integer.parseInt(parts[1].split("=")[1]);
            if (value > props.getCorbon().getThreshold()) {
                log.error("ALARM! Co2 exceeded: {}°S", value);
            } else {
                log.info("Co2 reading normal: {}°S", value);
            }
        } catch (Exception e) {
            log.error("Failed to parse Co2 message: {}", message, e);
        }
    }
}
