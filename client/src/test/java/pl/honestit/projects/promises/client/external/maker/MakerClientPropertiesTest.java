package pl.honestit.projects.promises.client.external.maker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(MakerClientProperties.class)
@ImportAutoConfiguration(ConfigurationPropertiesAutoConfiguration.class)
@TestPropertySource(properties = {
        MakerClientProperties.PREFIX + ".exchange-name=" + MakerClientPropertiesTest.EXCHANGE_NAME,
        MakerClientProperties.PREFIX + ".queue-name=" + MakerClientPropertiesTest.QUEUE_NAME,
        MakerClientProperties.PREFIX + ".routing-key=" + MakerClientPropertiesTest.ROUTING_KEY,
})
@ExtendWith(SpringExtension.class)
class MakerClientPropertiesTest {

    static final String EXCHANGE_NAME = "test-exchange-name";
    static final String QUEUE_NAME = "test-queue-name";
    static final String ROUTING_KEY = "test-routing-key";

    @Autowired
    MakerClientProperties cut;

    @Test
    @DisplayName("Should configure maker client properties with required values")
    void test1() {
        Assertions.assertNotNull(cut);
        Assertions.assertEquals(EXCHANGE_NAME, cut.getExchangeName());
        Assertions.assertEquals(QUEUE_NAME, cut.getQueueName());
        Assertions.assertEquals(ROUTING_KEY, cut.getRoutingKey());
    }


}