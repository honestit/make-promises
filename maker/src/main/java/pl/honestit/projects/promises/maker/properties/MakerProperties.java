package pl.honestit.projects.promises.maker.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("promises.services.maker")
@Data
public class MakerProperties {

    private String exchangeName;
    private String queueName;
    private String routingKey;
}
