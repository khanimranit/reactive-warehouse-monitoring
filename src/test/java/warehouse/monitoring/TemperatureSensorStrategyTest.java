package warehouse.monitoring;

import com.example.warehouse.config.SensorData;
import com.example.warehouse.sensors.TemperatureSensor;
import org.junit.jupiter.api.Test;

class TemperatureSensorStrategyTest {

    @Test
    void testThresholdExceeded() {
        SensorData props = new SensorData();
        SensorData.SensorConfig temp = new SensorData.SensorConfig();
        temp.setThreshold(35);
        props.setTemperature(temp);

        TemperatureSensor strategy = new TemperatureSensor(props);
        strategy.checkThreshold("sensor_id=t1; value=36");
    }
}
