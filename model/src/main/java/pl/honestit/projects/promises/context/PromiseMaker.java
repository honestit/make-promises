package pl.honestit.projects.promises.context;

import pl.honestit.projects.promises.model.promise.Promise;

public interface PromiseMaker {

    boolean make(Promise promise);
}
