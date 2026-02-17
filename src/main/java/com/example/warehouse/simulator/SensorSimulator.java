package com.example.warehouse.simulator;

import com.example.warehouse.config.SensorData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SensorSimulator {

    private final SensorData config;
    private final Random random = new Random();

    @PostConstruct
    public void startSimulation() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        SensorData.SensorConfig temp = config.getTemperature();
        scheduler.scheduleAtFixedRate(() ->
                        sendUdpMessage("sensor_id=t1; value=" + randomValue(temp.getSimulation()), temp.getPort()),
                0, temp.getSimulation().getIntervalMs(), TimeUnit.MILLISECONDS);

        SensorData.SensorConfig hum = config.getHumidity();
        scheduler.scheduleAtFixedRate(() ->
                        sendUdpMessage("sensor_id=h1; value=" + randomValue(hum.getSimulation()), hum.getPort()),
                0, hum.getSimulation().getIntervalMs(), TimeUnit.MILLISECONDS);
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
            System.out.println("Simulated message sent: " + message + " to port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
