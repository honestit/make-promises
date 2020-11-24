package pl.honestit.projects.promises.maker.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.honestit.projects.promises.maker.DataHelper;
import pl.honestit.projects.promises.maker.model.RejectedPromiseEntity;
import pl.honestit.projects.promises.model.promise.Promise;
import pl.honestit.projects.promises.model.promise.RejectReason;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Specification: When promise is converted as rejected one")
class RejectedPromiseEntityConverterTest {

    RejectedPromiseEntityConverter cut;

    @BeforeEach
    void setUp() {
        cut = new RejectedPromiseEntityConverter();
    }

    @Test
    @DisplayName("- should convert with expected data")
    void shouldConvertFromPromise() {
        ZonedDateTime when = ZonedDateTime.now();
        ZonedDateTime till = ZonedDateTime.now();
        Promise promise = DataHelper.promise("alex", "a promise", "a friend", when, till);
        RejectedPromiseEntity expected = DataHelper.rejectedPromiseEntity("alex", "a promise", "a friend", when, till);

        RejectedPromiseEntity rejectedPromise = cut.from(promise);

        Assertions.assertThat(rejectedPromise)
                .isNotNull()
                .isEqualToComparingOnlyGivenFields(expected,
                        "who", "what", "whom", "when", "till");
    }

    @ParameterizedTest
    @DisplayName("- should convert with uncompleted data reason when required data is missing")
    @MethodSource("uncompletedPromises")
    void shouldConvertWithUncompletedDataReasonWhenRequiredDataIsMissing(Promise uncompletedPromise) {
        RejectedPromiseEntity rejectedPromiseEntity = cut.from(uncompletedPromise);

        assertEquals(RejectReason.INCOMPLETE_DATA, rejectedPromiseEntity.getReason(), "Did not set expected reason type");
    }

    @Test
    @DisplayName("- should convert with past deadline reason when deadline is before creation")
    void shouldConvertWithPastDeadlineReasonWhenDeadlineIsBeforeCreation() {
        ZonedDateTime when = ZonedDateTime.now();
        ZonedDateTime till = when.minusMinutes(1);
        Promise outdatedPromise = DataHelper.promise("alex", "a promise", "a friend", when, till);

        RejectedPromiseEntity rejectedPromiseEntity = cut.from(outdatedPromise);

        assertEquals(RejectReason.PAST_DEADLINE, rejectedPromiseEntity.getReason(), "Did not set expected reason type");
    }

    @Test
    @DisplayName("- should convert with no reason if promise is somehow valid")
    void shouldConvertWithNoReasonIfPromiseIsSomehowValid() {
        Promise promise = DataHelper.promise("alex", "a promise", "a friend", ZonedDateTime.now(), ZonedDateTime.now());

        RejectedPromiseEntity rejectedPromiseEntity = cut.from(promise);

        assertNull(rejectedPromiseEntity.getReason(), "Valid promise got a reject reason");
    }

    static Stream<Promise> uncompletedPromises() {
        return Stream.of(
                DataHelper.promise(null, null, null, null, null),
                DataHelper.promise("alex", null, null, null, null),
                DataHelper.promise("alex", "a promise", null, null, null),
                DataHelper.promise("alex", "a promise", "a friend", null, null),
                DataHelper.promise("alex", "a promise", "a friend", ZonedDateTime.now(), null)
        );
    }

}