package pl.honestit.projects.promises.maker.executor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.honestit.projects.promises.maker.DataHelper;
import pl.honestit.projects.promises.maker.converter.RejectedPromiseEntityConverter;
import pl.honestit.projects.promises.maker.model.RejectedPromiseEntity;
import pl.honestit.projects.promises.maker.repository.RejectedPromiseEntityRepository;
import pl.honestit.projects.promises.model.promise.Promise;
import pl.honestit.projects.promises.model.promise.RejectReason;

import java.time.ZonedDateTime;

@DisplayName("Specification: When promise was not made")
@ExtendWith(MockitoExtension.class)
class RejectedPromiseExecutorTest {

    @Mock
    RejectedPromiseEntityConverter converter;

    @Mock
    RejectedPromiseEntityRepository repository;

    @InjectMocks
    RejectedPromiseExecutor cut;

    @Test
    @DisplayName("- should save rejected promise")
    void shouldSaveRejectedPromise() {
        Promise promise = DataHelper.promise("alex", null, "a friend", ZonedDateTime.now(), ZonedDateTime.now());
        RejectedPromiseEntity rejectedPromise = DataHelper.rejectedPromiseEntity("alex", null, "a friend",
                ZonedDateTime.now(), ZonedDateTime.now());
        rejectedPromise.setReason(RejectReason.INCOMPLETE_DATA);

        Mockito.when(converter.from(promise)).thenReturn(rejectedPromise);

        cut.rejectPromise(promise);

        Mockito.verify(repository, Mockito.times(1)).save(rejectedPromise);
    }

    @Test
    @DisplayName("- should throw exception when rejected promise doesn't have an author")
    void shouldThrowExceptionWhenRejectedPromiseDoesntHaveAnAuthor() {
        RejectedPromiseEntity rejectedPromise = RejectedPromiseEntity.builder()
                .who(null).build();

        Mockito.when(converter.from(ArgumentMatchers.any())).thenReturn(rejectedPromise);

        Assertions.assertThatThrownBy(() -> cut.rejectPromise(new Promise()))
                .hasMessageContaining("promise must have an author")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("- should throw exception when rejected promise doesn't have an reason")
    void shouldThrowExceptionWhenRejectedPromiseDoesntHaveAnReason() {
        RejectedPromiseEntity rejectedPromise = RejectedPromiseEntity.builder()
                .who("alex").reason(null).build();

        Mockito.when(converter.from(ArgumentMatchers.any())).thenReturn(rejectedPromise);

        Assertions.assertThatThrownBy(() -> cut.rejectPromise(new Promise()))
                .hasMessageContaining("Unspecified reason")
                .isInstanceOf(IllegalStateException.class);
    }
}