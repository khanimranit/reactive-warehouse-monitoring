package com.example.warehouse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "warehouse.sensors")
@Data
public class SensorData {

    private SensorConfig temperature;
    private SensorConfig humidity;
    private SensorConfig corbon;

    @Data
    public static class SensorConfig {
        private int port;
        private int threshold;
        private Simulation simulation;

        @Data
        public static class Simulation {
            private int minValue;
            private int maxValue;
            private int intervalMs;
        }
    }
}
