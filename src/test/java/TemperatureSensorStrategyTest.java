package com.example.warehouse.strategy;

import com.example.warehouse.config.SensorData;
import org.junit.jupiter.api.Test;

class TemperatureSensorStrategyTest {

    @Test
    void testThresholdExceeded() {
        SensorData props = new SensorData();
        SensorData.SensorConfig temp = new SensorData.SensorConfig();
        temp.setThreshold(35);
        props.setTemperature(temp);

        TemperatureSensorStrategy strategy = new TemperatureSensorStrategy(props);
        strategy.checkThreshold("sensor_id=t1; value=36"); // should trigger alarm
    }
}
