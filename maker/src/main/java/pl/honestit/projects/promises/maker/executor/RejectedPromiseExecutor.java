package pl.honestit.projects.promises.maker.executor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.honestit.projects.promises.maker.converter.RejectedPromiseEntityConverter;
import pl.honestit.projects.promises.maker.model.RejectedPromiseEntity;
import pl.honestit.projects.promises.maker.repository.RejectedPromiseEntityRepository;
import pl.honestit.projects.promises.model.promise.Promise;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RejectedPromiseExecutor {

    private final RejectedPromiseEntityRepository repository;
    private final RejectedPromiseEntityConverter converter;

    @Transactional
    public void rejectPromise(Promise promise) {
        log.debug("Promise to be rejected: {}", promise);

        RejectedPromiseEntity rejectedPromise = converter.from(promise);
        log.debug("Rejected promise to be saved: {}", rejectedPromise);

        if (rejectedPromise.getWho() == null) {
            log.warn("Promise to be rejected doesn't have an author!");
            throw new IllegalArgumentException("Even of it is rejected, a promise must have an author");
        }

        if (rejectedPromise.getReason() == null) {
            log.warn("Promise to be rejected doesn't have a reason");
            throw new IllegalStateException("Unspecified reason for rejected promise");
        }

        repository.save(rejectedPromise);
        log.debug("Rejected promise saved: {}", rejectedPromise);
    }

}
