package sk.umb.it.arch.pubsubcache.iot.service;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("iot_data");
    }

    private static class ReceiverConfig {
        @Bean
        public Queue myQueue() {
            return new Queue("", true);
        }

        @Bean
        public Binding binding(FanoutExchange fanout, Queue myQueue) {
            return BindingBuilder.bind(myQueue).to(fanout);
        }
    }
}
