package pl.honestit.projects.promises.maker.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.honestit.projects.promises.maker.DataHelper;
import pl.honestit.projects.promises.maker.model.PromiseEntity;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("PromiseEntity Validator Spec")
class PromiseEntityValidatorTest {

    PromiseEntityValidator cut;

    @BeforeEach
    void setUp() {
        cut = new PromiseEntityValidator();
    }

    @Test
    @DisplayName("Should pass when promise is valid")
    void shouldPassWhenPromiseIsValid() {
        PromiseEntity promise = DataHelper.promiseEntity("alex", "a promise", "whom",
                ZonedDateTime.now(), ZonedDateTime.now().plusMinutes(1));

        boolean valid = cut.isValid(promise);

        assertTrue(valid, "Valid promise did not pass");
    }

    @ParameterizedTest
    @DisplayName("Should not pass when promise is invalid")
    @MethodSource("invalidPromises")
    void shouldNotPassWhenPromiseIsInvalid(PromiseEntity invalidPromise) {
        boolean valid = cut.isValid(invalidPromise);

        assertFalse(valid, "Invalid promise did pass");
    }

    @Test
    @DisplayName("Should not pass when promise deadline is in the past")
    void shouldNotPassWhenPromiseDeadlineIsInThePast() {
        PromiseEntity outdatedPromise = DataHelper.promiseEntity("alex", "a promise", "a friend",
                ZonedDateTime.now(), ZonedDateTime.now().minusMinutes(1));

        boolean valid = cut.isValid(outdatedPromise);

        assertFalse(valid, "Invalid promise did pass");
    }

    static Stream<PromiseEntity> invalidPromises() {
        return Stream.of(
                DataHelper.promiseEntity(null, "a promise", "a friend", ZonedDateTime.now(), ZonedDateTime.now()),
                DataHelper.promiseEntity("alex", null, "a friend", ZonedDateTime.now(), ZonedDateTime.now()),
                DataHelper.promiseEntity("alex", "a promise", null, ZonedDateTime.now(), ZonedDateTime.now()),
                DataHelper.promiseEntity("alex", "a promise", "a friend", null, ZonedDateTime.now()),
                DataHelper.promiseEntity("alex", "a promise", "a friend", ZonedDateTime.now(), null)
        );
    }


}