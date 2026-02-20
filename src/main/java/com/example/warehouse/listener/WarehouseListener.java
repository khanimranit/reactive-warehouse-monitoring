package com.example.warehouse.listener;

import com.example.warehouse.service.CentralMonitoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class WarehouseListener {

    private final CentralMonitoringService centralService;

    public void startSensorListener(int port, String sensorType) {
        Flux.<String>create(sink -> new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket(port)) {
                byte[] buffer = new byte[1024];
               log.info("UDP listener started on port {}  for sensorType {} " ,port,sensorType);

                while (!sink.isCancelled()) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

                    sink.next(message);
                }
            } catch (Exception e) {
                sink.error(e);
            } finally {
                sink.complete();
            }
        }, "udp-listener-" + port).start()).doOnNext(message -> centralService.receiveMeasurement(sensorType, message)).doOnError(Throwable::printStackTrace).subscribe();
    }
}