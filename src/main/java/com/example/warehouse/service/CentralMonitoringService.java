package com.example.warehouse.service;

import com.example.warehouse.strategy.SensorStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CentralMonitoringService {

    private final Map<String, SensorStrategy> strategyMap;

    public void receiveMeasurement(String sensorType, String message) {
        SensorStrategy strategy = strategyMap.get(sensorType);
        if (strategy != null) {
            strategy.checkThreshold(message);
        } else {
            System.err.println("No strategy for sensor type: " + sensorType);
        }
    }
}
