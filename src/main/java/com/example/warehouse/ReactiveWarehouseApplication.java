package com.example.warehouse;

import com.example.warehouse.config.SensorData;
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
	private final SensorData props;


	public static void main(String[] args) {
		SpringApplication.run(ReactiveWarehouseApplication.class, args);
	}

	@Override
	public void run(String... args) {
		warehouseListener.startSensorListener(props.getTemperature().getPort(), "Temperature");
		warehouseListener.startSensorListener(props.getHumidity().getPort(), "Humidity");
		warehouseListener.startSensorListener(props.getCorbon().getPort(), "Corbon");
	}
}
