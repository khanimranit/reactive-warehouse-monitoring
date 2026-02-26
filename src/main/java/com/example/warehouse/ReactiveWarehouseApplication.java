package com.example.warehouse;

import com.example.warehouse.config.SensorData;
import com.example.warehouse.listener.Warehouse2Listener;
import com.example.warehouse.listener.WarehouseListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class ReactiveWarehouseApplication implements CommandLineRunner {

	private final WarehouseListener warehouseListener;
	private final Warehouse2Listener warehouseListener2;
	private final SensorData props;


	public static void main(String[] args) {
		SpringApplication.run(ReactiveWarehouseApplication.class, args);
	}

	@Override
	public void run(String... args) {
		warehouseListener.startSensorListener(props.getTemperature().getPort(), "temperature");
		warehouseListener.startSensorListener(props.getHumidity().getPort(), "humidity");
		warehouseListener2.startSensorListener(props.getCorbon().getPort(), "corbon");
		warehouseListener2.startSensorListener(props.getLocation().getPort(), "location");
	}
}
