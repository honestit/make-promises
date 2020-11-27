package pl.honestit.projects.promises.maker.listener;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.honestit.projects.promises.context.PromiseMaker;
import pl.honestit.projects.promises.maker.DataHelper;
import pl.honestit.projects.promises.maker.executor.RejectedPromiseExecutor;
import pl.honestit.projects.promises.model.promise.Promise;

import java.time.ZonedDateTime;

@ExtendWith(MockitoExtension.class)
@DisplayName("Specification: When promise is received")
class PromiseListenerTest {

    @Mock
    PromiseMaker promiseMaker;

    @Mock
    RejectedPromiseExecutor rejectedPromiseExecutor;

    @InjectMocks
    PromiseListener cut;

    @Test
    @DisplayName("- should make a promise")
    void shouldMakePromise() {
        Promise promise = DataHelper.promise("alex", "a promise", "a friend", ZonedDateTime.now(), ZonedDateTime.now());
        Mockito.when(promiseMaker.make(promise)).thenReturn(true);

        cut.receivedPromise(promise);

        Mockito.verify(promiseMaker, Mockito.times(1)).make(promise);
        Mockito.verifyNoInteractions(rejectedPromiseExecutor);
    }

    @Test
    @DisplayName("- should reject a promise when not made")
    void shouldRejectPromiseWhenNotMade() {
        Promise promise = DataHelper.promise("alex", "a promise", "a friend", ZonedDateTime.now(), ZonedDateTime.now());
        Mockito.when(promiseMaker.make(promise)).thenReturn(false);

        cut.receivedPromise(promise);

        Mockito.verify(rejectedPromiseExecutor, Mockito.times(1)).rejectPromise(promise);
    }

}