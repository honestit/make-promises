package pl.honestit.projects.promises.maker.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Specification: When app is starting")
@Import(MakerProperties.class)
@ImportAutoConfiguration(ConfigurationPropertiesAutoConfiguration.class)
@TestPropertySource(properties = {
        "promises.services.maker.exchange-name=" + MakerPropertiesTest.EXCHANGE_NAME,
        "promises.services.maker.queue-name=" + MakerPropertiesTest.QUEUE_NAME,
        "promises.services.maker.routing-key=" + MakerPropertiesTest.ROUTING_KEY
})
@ExtendWith(SpringExtension.class)
class MakerPropertiesTest {

    static final String EXCHANGE_NAME = "test-exchange-name";
    static final String QUEUE_NAME = "test-queue-name";
    static final String ROUTING_KEY = "test-routing-key";

    @Autowired
    MakerProperties makerProperties;

    @Test
    @DisplayName("- should maker properties be set")
    void test1() {
        assertNotNull(makerProperties);
        assertEquals(EXCHANGE_NAME, makerProperties.getExchangeName());
        assertEquals(QUEUE_NAME, makerProperties.getQueueName());
        assertEquals(ROUTING_KEY, makerProperties.getRoutingKey());
    }

}