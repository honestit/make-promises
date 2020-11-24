package pl.honestit.projects.promises.maker.validation;

import org.springframework.stereotype.Component;
import pl.honestit.projects.promises.maker.model.RejectedPromiseEntity;
import pl.honestit.projects.promises.model.promise.RejectReason;

@Component
public class RejectedPromiseEntityValidator {

    public RejectReason getReason(RejectedPromiseEntity entity) {
        if (entity.getWhat() == null
                || entity.getWhom() == null
                || entity.getWhen() == null
                || entity.getTill() == null) {
            return RejectReason.INCOMPLETE_DATA;
        } else if (entity.getTill().isBefore(entity.getWhen())) {
            return RejectReason.PAST_DEADLINE;
        } else {
            return null;
        }
    }
}
