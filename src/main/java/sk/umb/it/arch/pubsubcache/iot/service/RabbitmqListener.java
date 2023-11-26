package sk.umb.it.arch.pubsubcache.iot.service;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqListener {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SimpMessagingTemplate template;

    private static final String EXCHANGE_NAME = "iot-data";
    @RabbitListener(autoStartup="true",
            bindings=@QueueBinding(value = @Queue,
                    exchange=@Exchange(name=EXCHANGE_NAME,
                            type= ExchangeTypes.FANOUT,
                            durable = "true")))
    public void consoleOutputReceiver(String message) {
        System.out.println("Received message: " + message);
    }

    @RabbitListener(autoStartup="true",
            bindings=@QueueBinding(value = @Queue,
                    exchange=@Exchange(name=EXCHANGE_NAME,
                            type= ExchangeTypes.FANOUT,
                            durable = "true")))
    public void redisOutputReceiver(String message) {
        redisTemplate.opsForList().leftPush("iot-data", message);
    }
}
