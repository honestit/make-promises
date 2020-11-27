package pl.honestit.projects.promises.client.external.maker;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = MakerClientProperties.PREFIX)
@Data
public class MakerClientProperties {

    public static final String PREFIX = "promises.services.maker.client";

    private String queueName;
    private String exchangeName;
    private String routingKey;
}
