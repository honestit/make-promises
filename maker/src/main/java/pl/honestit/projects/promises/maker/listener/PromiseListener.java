package pl.honestit.projects.promises.maker.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.honestit.projects.promises.context.PromiseMaker;
import pl.honestit.projects.promises.maker.executor.RejectedPromiseExecutor;
import pl.honestit.projects.promises.model.promise.Promise;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromiseListener {

    private final PromiseMaker promiseMaker;
    private final RejectedPromiseExecutor rejectedPromiseExecutor;

    @Transactional
    public void receivedPromise(Promise promise) {
        log.debug("Received promise: {}", promise);

        boolean made = promiseMaker.make(promise);
        if (made) {
            log.debug("Promise was successfully made");
        } else {
            log.warn("Promise somehow was not made");
            rejectedPromiseExecutor.rejectPromise(promise);
            log.warn("Promise was rejected");
        }

    }
}
