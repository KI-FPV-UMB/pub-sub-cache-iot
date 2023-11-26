package sk.umb.it.arch.pubsubcache.iot.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Map;

public class IotDataPublisher {
    private static final int SLEEP_TIME_SEC = 2;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String EXCHANGE_NAME = "iot-data";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");


        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);

            while (true) {
                String message = getDataAsText();

                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");

                Thread.sleep(SLEEP_TIME_SEC * 1000);
            }
        }
    }

    private static String getDataAsText() throws Exception {
        Map<String, Object> data = IotDeviceMock.readData();
        return objectMapper.writeValueAsString(data);
    }
}
