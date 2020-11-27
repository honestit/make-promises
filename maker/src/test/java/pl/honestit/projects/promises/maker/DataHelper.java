package pl.honestit.projects.promises.maker;

import pl.honestit.projects.promises.maker.model.PromiseEntity;
import pl.honestit.projects.promises.maker.model.RejectedPromiseEntity;
import pl.honestit.projects.promises.model.friend.Friend;
import pl.honestit.projects.promises.model.promise.Deadline;
import pl.honestit.projects.promises.model.promise.Promise;

import java.time.ZonedDateTime;
import java.util.Optional;

public class DataHelper {

    public static PromiseEntity promiseEntity(String who, String what, String whom, ZonedDateTime when, ZonedDateTime till) {
        return new PromiseEntity(null, who, whom, what, when, till, null, null);
    }

    public static Promise promise(String who, String what, String whom, ZonedDateTime when, ZonedDateTime till) {
        return new Promise(who, what,
                new Friend(whom, null, null, null), when,
                new Deadline(
                        Optional.ofNullable(till).map(ZonedDateTime::getZone).orElse(null),
                        Optional.ofNullable(till).map(ZonedDateTime::toLocalDate).orElse(null),
                        Optional.ofNullable(till).map(ZonedDateTime::toLocalTime).orElse(null)));
    }

    public static RejectedPromiseEntity rejectedPromiseEntity(String who, String what, String whom,
                                                              ZonedDateTime when, ZonedDateTime till) {
        return new RejectedPromiseEntity(null, who, what, whom, when, till, null);
    }
}
