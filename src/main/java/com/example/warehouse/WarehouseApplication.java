package com.example.warehouse;

import com.example.warehouse.config.SensorProperties;
import com.example.warehouse.service.WarehouseListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class WarehouseApplication implements CommandLineRunner {

	private final WarehouseListener warehouseListener;
	private final SensorProperties props;

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}

	@Override
	public void run(String... args) {
		warehouseListener.startSensorListener(props.getTemperature().getPort(), "Temperature");
		warehouseListener.startSensorListener(props.getHumidity().getPort(), "Humidity");
	}
}
