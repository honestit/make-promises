package pl.honestit.projects.promises.maker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.honestit.projects.promises.maker.listener.PromiseListener;
import pl.honestit.projects.promises.maker.properties.MakerProperties;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AMQPConfig {

    private final MakerProperties makerProperties;

    @Bean
    public Queue makerQueue() {
        log.debug("Creating queue bean for: {}", makerProperties.getQueueName());
        return new Queue(makerProperties.getQueueName(), true);
    }

    @Bean
    public DirectExchange makerExchange() {
        log.debug("Creating exchange bean for: {}", makerProperties.getExchangeName());
        return new DirectExchange(makerProperties.getExchangeName(), true, false);
    }

    @Bean
    public Binding makerBinding(Queue makerQueue, DirectExchange makerExchange) {
        log.debug("Creating binding bean for queue '{}' and exchange '{}'", makerQueue, makerExchange);
        return BindingBuilder.bind(makerQueue).to(makerExchange).with(makerProperties.getRoutingKey());
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(PromiseListener promiseListener, Jackson2JsonMessageConverter converter) {
        log.debug("Creating message listener adapter for receiver: {}", promiseListener);
        MessageListenerAdapter adapter = new MessageListenerAdapter(promiseListener, converter);
        adapter.setDefaultListenerMethod("receivedPromise");
        return adapter;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        log.debug("Creating bean for AMQP Container with connectionFactory '{}' and messageListenerAdapter: '{}'", connectionFactory, messageListenerAdapter);
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(makerProperties.getQueueName());
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
