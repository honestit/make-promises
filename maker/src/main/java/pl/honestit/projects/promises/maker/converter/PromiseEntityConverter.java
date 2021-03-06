package pl.honestit.projects.promises.maker.converter;

import org.springframework.stereotype.Component;
import pl.honestit.projects.promises.maker.model.PromiseEntity;
import pl.honestit.projects.promises.model.friend.Friend;
import pl.honestit.projects.promises.model.friend.FriendIdentity;
import pl.honestit.projects.promises.model.promise.Deadline;
import pl.honestit.projects.promises.model.promise.Promise;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class PromiseEntityConverter {

    public PromiseEntity from(Promise promise) {
        return PromiseEntity.builder()
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
    }

    private ZonedDateTime asZoneDateTime(Deadline deadline) {
        if (deadline == null) return null;
        if (deadline.getZoneId() == null) return null;
        if (deadline.getTillDate() == null) return null;
        if (deadline.getTillTime() == null) return null;
        return ZonedDateTime.of(deadline.getTillDate().atTime(deadline.getTillTime()), deadline.getZoneId());
    }
}
