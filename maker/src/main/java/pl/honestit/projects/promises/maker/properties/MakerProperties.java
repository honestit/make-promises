package pl.honestit.projects.promises.maker.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties("promises.services.maker")
@Data
public class MakerProperties {

    @NotBlank
    private String exchangeName;
    @NotBlank
    private String queueName;
    @NotBlank
    private String routingKey;
}
