package pl.honestit.projects.promises.maker.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.honestit.projects.promises.maker.DataHelper;
import pl.honestit.projects.promises.maker.listener.PromiseListener;
import pl.honestit.projects.promises.maker.properties.MakerProperties;
import pl.honestit.projects.promises.maker.repository.PromiseEntityRepository;
import pl.honestit.projects.promises.maker.repository.RejectedPromiseEntityRepository;
import pl.honestit.projects.promises.model.promise.Promise;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@DisplayName("Integration: On received promise")
@SpringBootTest
@ActiveProfiles("integration")
@Testcontainers
public class ReceivePromiseIT {

    @Container
    static RabbitMQContainer rabbitMQ = new RabbitMQContainer(DockerImageName.parse("rabbitmq:management"))
            .withExposedPorts(5671, 5672, 15672)
            .withReuse(true);

    @Container
    static PostgreSQLContainer<?> postgreSQL = new PostgreSQLContainer(DockerImageName.parse("postgres:10"))
            .withDatabaseName("promises")
            .withUsername("maker")
            .withPassword("maker");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQL::getUsername);
        registry.add("spring.datasource.password", postgreSQL::getPassword);
        String amqpUrl = rabbitMQ.getAmqpUrl();
        System.out.println(amqpUrl);
        registry.add("spring.rabbitmq.addresses", rabbitMQ::getAmqpUrl);
    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PromiseListener promiseListener;

    @Autowired
    PromiseEntityRepository promiseEntityRepository;

    @Autowired
    RejectedPromiseEntityRepository rejectedPromiseEntityRepository;

    @Autowired
    MakerProperties makerProperties;

    @BeforeEach
    void assertEmptyDatabase() {
        Assertions.assertEquals(0, promiseEntityRepository.count());
        Assertions.assertEquals(0, rejectedPromiseEntityRepository.count());
    }

    @AfterEach
    void clearDatabase() {
        promiseEntityRepository.deleteAll();
        rejectedPromiseEntityRepository.deleteAll();
    }

    @Test
    @DisplayName("- should be available all components")
    void shouldBeAvailableAllComponents() {
        Assertions.assertNotNull(rabbitTemplate, "RabbitTemplate was not set");
        Assertions.assertNotNull(promiseListener, "PromiseListener was not set");
    }

    @Test
    @DisplayName("- should save valid promise")
    void shouldSaveValidPromise() throws JsonProcessingException, UnsupportedEncodingException {
        Promise validPromise = DataHelper.promise("alex", "a promise", "a friend",
                ZonedDateTime.now(), ZonedDateTime.now());

        rabbitTemplate.convertAndSend(makerProperties.getExchangeName(), makerProperties.getRoutingKey(), validPromise);

        org.awaitility.Awaitility.given()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> Assertions.assertEquals(1, promiseEntityRepository.count(),
                        "Promise was not saved"));

    }

    @Test
    @DisplayName("- should reject invalid promise")
    void shouldRejectInvalidPromise() throws JsonProcessingException {
        Promise promise = DataHelper.promise("alex", null, "a friend", ZonedDateTime.now(), ZonedDateTime.now());
        System.out.println(objectMapper.writeValueAsString(promise));

        rabbitTemplate.convertAndSend(makerProperties.getExchangeName(), makerProperties.getRoutingKey(), promise);

        org.awaitility.Awaitility.given()
                .atMost(3, TimeUnit.SECONDS)
                .untilAsserted(() -> Assertions.assertEquals(1, rejectedPromiseEntityRepository.count(),
                        "Promise was not rejected"));
    }

}
