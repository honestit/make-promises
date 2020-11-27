package pl.honestit.projects.promises.maker.converter;

import org.springframework.stereotype.Component;
import pl.honestit.projects.promises.maker.model.RejectedPromiseEntity;
import pl.honestit.projects.promises.model.friend.Friend;
import pl.honestit.projects.promises.model.friend.FriendIdentity;
import pl.honestit.projects.promises.model.promise.Deadline;
import pl.honestit.projects.promises.model.promise.Promise;
import pl.honestit.projects.promises.model.promise.RejectReason;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class RejectedPromiseEntityConverter {

    public RejectedPromiseEntity from(Promise promise) {
        RejectedPromiseEntity rejectedPromiseEntity = RejectedPromiseEntity.builder()
                .who(promise.getWho())
                .what(promise.getWhat())
                .whom(Optional.ofNullable(promise.getWhom())
                        .map(Friend::getIdentity)
                        .map(FriendIdentity::getUsername)
                        .orElse(Optional.ofNullable(promise.getWhom())
                                .map(Friend::getName)
                                .orElse(null)
                        ))
                .when(promise.getWhen())
                .till(asZoneDateTime(promise.getTill()))
                .build();
        updateWithReason(rejectedPromiseEntity);
        return rejectedPromiseEntity;
    }

    private ZonedDateTime asZoneDateTime(Deadline deadline) {
        if (deadline == null) return null;
        if (deadline.getZoneId() == null) return null;
        if (deadline.getTillDate() == null) return null;
        if (deadline.getTillTime() == null) return null;
        return ZonedDateTime.of(deadline.getTillDate().atTime(deadline.getTillTime()), deadline.getZoneId());
    }

    private void updateWithReason(RejectedPromiseEntity rejectedPromiseEntity) {
        if (rejectedPromiseEntity.getWhat() == null
                || rejectedPromiseEntity.getWhom() == null
                || rejectedPromiseEntity.getWhen() == null
                || rejectedPromiseEntity.getTill() == null) {
            rejectedPromiseEntity.setReason(RejectReason.INCOMPLETE_DATA);
        } else if (rejectedPromiseEntity.getTill().isBefore(rejectedPromiseEntity.getWhen())) {
            rejectedPromiseEntity.setReason(RejectReason.PAST_DEADLINE);
        }
    }
}
