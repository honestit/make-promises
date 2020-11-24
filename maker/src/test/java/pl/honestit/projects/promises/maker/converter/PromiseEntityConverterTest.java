package pl.honestit.projects.promises.maker.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.honestit.projects.promises.maker.DataHelper;
import pl.honestit.projects.promises.maker.model.PromiseEntity;
import pl.honestit.projects.promises.model.promise.Promise;

import java.time.ZonedDateTime;

@DisplayName("Specification: When PromiseEntity is converted")
class PromiseEntityConverterTest {

    PromiseEntityConverter cut;

    @BeforeEach
    void setUp() {
        cut = new PromiseEntityConverter();
    }

    @Test
    @DisplayName("- should convert with expected data")
    void shouldConvertPromise() {
        ZonedDateTime when = ZonedDateTime.now();
        ZonedDateTime till = ZonedDateTime.now();
        Promise promise = DataHelper.promise("alex", "a promise", "a friend", when, till);
        PromiseEntity expected = DataHelper.promiseEntity("alex", "a promise", "a friend", when, till);

        PromiseEntity entity = cut.from(promise);

        Assertions.assertThat(entity)
                .isNotNull()
                .isEqualToComparingOnlyGivenFields(
                        expected,
                        "who", "what", "whom", "when", "till"
                );
    }

}