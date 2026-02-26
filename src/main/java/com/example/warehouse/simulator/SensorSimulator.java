package com.example.warehouse.simulator;

import com.example.warehouse.config.SensorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class SensorSimulator {

    private final SensorData config;
    private final Random random = new Random();

    @Scheduled(initialDelayString = "0", fixedRateString = "#{@sensorData.temperature.simulation.intervalMs}")
    public void sendTemperature() {
        SensorData.SensorConfig temp = config.getTemperature();
        sendUdpMessage("sensor_id=t1; value=" + randomValue(temp.getSimulation()), temp.getPort());
    }

    @Scheduled(initialDelayString = "0", fixedRateString = "#{@sensorData.humidity.simulation.intervalMs}")
    public void sendHumidity() {
        SensorData.SensorConfig hum = config.getHumidity();
        sendUdpMessage("sensor_id=h1; value=" + randomValue(hum.getSimulation()), hum.getPort());
    }
    @Scheduled(initialDelayString = "0", fixedRateString = "#{@sensorData.humidity.simulation.intervalMs}")
    public void sendCorbon() {
        SensorData.SensorConfig co = config.getCorbon();
        sendUdpMessage("sensor_id=co2; value=" + randomValue(co.getSimulation()), co.getPort());
    }

    @Scheduled(initialDelayString = "0", fixedRateString = "#{@sensorData.location.simulation.intervalMs}")
    public void sendLocationData() {
        SensorData.SensorConfig co = config.getLocation();
        sendUdpMessage("23; value=" + randomValue(co.getSimulation()), co.getPort());
    }

    private int randomValue(SensorData.SensorConfig.Simulation sim) {
        return sim.getMinValue() + random.nextInt(sim.getMaxValue() - sim.getMinValue() + 1);
    }

    private void sendUdpMessage(String message, int port) {
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] buffer = message.getBytes();
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
           // log.info("Simulated message sent: {} to port {}", message, port);
        } catch (Exception e) {
            log.error("Failed to send simulated message: {} to port {}", message, port, e);
        }
    }
}
