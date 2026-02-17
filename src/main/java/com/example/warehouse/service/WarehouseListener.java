package com.example.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WarehouseListener {

    private final CentralMonitoringService centralService;

    public void startSensorListener(int port, String sensorType) {
        Flux.interval(Duration.ofMillis(500))
                .flatMap(tick -> Flux.create(sink -> {
                    try (DatagramSocket socket = new DatagramSocket(port)) {
                        byte[] buffer = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                        centralService.receiveMeasurement(sensorType, message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sink.complete();
                }))
                .subscribe();
        System.out.println("Reactive listener started for " + sensorType + " on UDP port " + port);
    }
}
