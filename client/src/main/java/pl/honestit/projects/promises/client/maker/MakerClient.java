package pl.honestit.projects.promises.client.maker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pl.honestit.projects.promises.client.properties.MakerClientProperties;
import pl.honestit.projects.promises.context.PromiseMaker;
import pl.honestit.projects.promises.model.promise.Promise;

@Component
@Slf4j
@RequiredArgsConstructor
public class MakerClient implements PromiseMaker {

    private final RabbitTemplate rabbitTemplate;
    private final MakerClientProperties properties;

    public boolean make(Promise promise) {
        log.debug("Making promise: {}", promise);

        rabbitTemplate.convertAndSend(properties.getExchangeName(), properties.getRoutingKey(), promise);

        log.debug("Promise send!");
        return true;
    }
}
