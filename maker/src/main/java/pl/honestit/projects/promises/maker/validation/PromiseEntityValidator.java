package pl.honestit.projects.promises.maker.validation;

import org.springframework.stereotype.Component;
import pl.honestit.projects.promises.maker.model.PromiseEntity;

@Component
public class PromiseEntityValidator {

    public boolean isValid(PromiseEntity promiseEntity) {
        if (promiseEntity == null) return false;
        if (promiseEntity.getWho() == null) return false;
        if (promiseEntity.getWhom() == null) return false;
        if (promiseEntity.getWhen() == null) return false;
        if (promiseEntity.getTill() == null) return false;
        return true;
    }
}
