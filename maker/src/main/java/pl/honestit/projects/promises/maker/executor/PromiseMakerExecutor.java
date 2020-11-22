package pl.honestit.projects.promises.maker.executor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.honestit.projects.promises.context.PromiseMaker;
import pl.honestit.projects.promises.maker.converter.PromiseEntityConverter;
import pl.honestit.projects.promises.maker.model.PromiseEntity;
import pl.honestit.projects.promises.maker.repository.PromiseEntityRepository;
import pl.honestit.projects.promises.maker.validation.PromiseEntityValidator;
import pl.honestit.projects.promises.model.promise.Promise;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromiseMakerExecutor implements PromiseMaker {

    private final PromiseEntityConverter converter;
    private final PromiseEntityValidator validator;
    private final PromiseEntityRepository repository;

    @Transactional
    public boolean make(Promise promise) {
        log.debug("Saving new promise: {}", promise);
        PromiseEntity entity = converter.from(promise);
        log.debug("Saving as entity: {}", entity);

        if (!validator.isValid(entity)) {
            log.debug("Promise is not valid for save!");
            return false;
        }

        repository.save(entity);
        log.debug("Promise saved! Start counting till deadline");
        return true;
    }
}
