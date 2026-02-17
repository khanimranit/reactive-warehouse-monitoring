package com.example.warehouse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "warehouse.sensors")
public class SensorProperties {
    private SensorConfig temperature;
    private SensorConfig humidity;

    @Data
    public static class SensorConfig {
        private int port;
        private int threshold;
    }
}