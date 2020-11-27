package pl.honestit.projects.promises.maker.executor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.honestit.projects.promises.maker.DataHelper;
import pl.honestit.projects.promises.maker.converter.PromiseEntityConverter;
import pl.honestit.projects.promises.maker.model.PromiseEntity;
import pl.honestit.projects.promises.maker.repository.PromiseEntityRepository;
import pl.honestit.projects.promises.maker.validation.PromiseEntityValidator;
import pl.honestit.projects.promises.model.promise.Promise;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DisplayName("Specification: When promise is going to be made")
class PromiseMakerExecutorTest {

    @Mock
    PromiseEntityConverter converter;

    @Mock
    PromiseEntityValidator validator;

    @Mock
    PromiseEntityRepository repository;

    @InjectMocks
    PromiseMakerExecutor cut;

    @Test
    @DisplayName("- should save valid promise")
    void shouldSaveValidPromise() {
        Promise promise = DataHelper.promise("alex", "a promise", "a friend", ZonedDateTime.now(), ZonedDateTime.now());
        PromiseEntity promiseEntity = DataHelper.promiseEntity("alex", "a promise", "a friend", ZonedDateTime.now(), ZonedDateTime.now());

        Mockito.when(converter.from(promise)).thenReturn(promiseEntity);
        Mockito.when(validator.isValid(promiseEntity)).thenReturn(true);

        boolean made = cut.make(promise);

        assertTrue(made, "Valid promises was not made");
        Mockito.verify(repository, Mockito.times(1)).save(promiseEntity);
    }

    @Test
    @DisplayName("- should not save invalid promise")
    void shouldNotSaveInvalidPromise() {
        Mockito.when(converter.from(ArgumentMatchers.any())).thenReturn(new PromiseEntity());
        Mockito.when(validator.isValid(ArgumentMatchers.any())).thenReturn(false);

        boolean made = cut.make(new Promise());

        assertFalse(made, "Invalid promise was made");
        Mockito.verifyNoInteractions(repository);
    }

}