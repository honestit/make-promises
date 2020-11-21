package pl.honestit.projects.promises.maker.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.honestit.projects.promises.model.promise.Promise;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromiseListener {

    public void receivedPromise(Promise promise) {
        log.debug("Received promise: {}", promise);
    }
}
