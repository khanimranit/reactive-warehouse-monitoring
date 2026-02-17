package com.example.warehouse.strategy;

import com.example.warehouse.config.SensorProperties;
import org.junit.jupiter.api.Test;

class TemperatureSensorStrategyTest {

    @Test
    void testThresholdExceeded() {
        SensorProperties props = new SensorProperties();
        SensorProperties.SensorConfig temp = new SensorProperties.SensorConfig();
        temp.setThreshold(35);
        props.setTemperature(temp);

        TemperatureSensorStrategy strategy = new TemperatureSensorStrategy(props);
        strategy.checkThreshold("sensor_id=t1; value=36"); // should trigger alarm
    }
}
