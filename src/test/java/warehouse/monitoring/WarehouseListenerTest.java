package warehouse.monitoring;

import com.example.warehouse.listener.WarehouseListener;
import com.example.warehouse.service.CentralMonitoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WarehouseListenerTest {

    @Mock
    private CentralMonitoringService centralMonitoringService;

    private WarehouseListener listener;

    @BeforeEach
    void setUp() {
        listener = new WarehouseListener(centralMonitoringService);
    }

    @Test
    void shouldForwardReceivedUdpMessageToCentralService() throws Exception {
        int port = randomFreePort();
        String sensorType = "temperature";
        String payload = "sensor_id=t1; value=30";

        listener.startSensorListener(port, sensorType);


        Thread.sleep(150);

        sendUdp("127.0.0.1", port, payload);

        verify(centralMonitoringService, timeout(3000)).receiveMeasurement(sensorType, payload);
    }

    @Test
    void shouldForwardMultipleMessages() throws Exception {
        int port = randomFreePort();
        String sensorType = "humidity";
        String payload1 = "sensor_id=h1; value=30";
        String payload2 = "sensor_id=h1; value=80";

        listener.startSensorListener(port, sensorType);
        Thread.sleep(150);

        sendUdp("127.0.0.1", port, payload1);
        sendUdp("127.0.0.1", port, payload2);

        verify(centralMonitoringService, timeout(3000)).receiveMeasurement(sensorType, payload1);
        verify(centralMonitoringService, timeout(3000)).receiveMeasurement(sensorType, payload2);
    }


    private static int randomFreePort() throws Exception {
        try (DatagramSocket s = new DatagramSocket(0)) {
            return s.getLocalPort();
        }
    }

    private static void sendUdp(String host, int port, String payload) throws Exception {
        byte[] data = payload.getBytes(StandardCharsets.UTF_8);
        InetAddress address = InetAddress.getByName(host);

        try (DatagramSocket socket = new DatagramSocket()) {
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
        }
    }
}