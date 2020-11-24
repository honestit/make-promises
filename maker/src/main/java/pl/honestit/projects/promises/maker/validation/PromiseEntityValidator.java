package pl.honestit.projects.promises.maker.validation;

import org.springframework.stereotype.Component;
import pl.honestit.projects.promises.maker.model.PromiseEntity;

import java.time.ZonedDateTime;

@Component
public class PromiseEntityValidator {

    public boolean isValid(PromiseEntity promiseEntity) {
        if (promiseEntity == null) return false;
        if (promiseEntity.getWho() == null) return false;
        if (promiseEntity.getWhat() == null) return false;
        if (promiseEntity.getWhom() == null) return false;
        if (promiseEntity.getWhen() == null) return false;
        if (promiseEntity.getTill() == null) return false;
        ZonedDateTime when = promiseEntity.getWhen();
        ZonedDateTime till = promiseEntity.getTill();
        if (when.isAfter(till)) return false;
        return true;
    }
}
