package pl.honestit.projects.promises.client.giving.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.honestit.projects.promises.client.makers.UserEntity;
import pl.honestit.projects.promises.client.makers.UserManager;
import pl.honestit.projects.promises.context.PromiseMaker;
import pl.honestit.projects.promises.model.friend.Friend;
import pl.honestit.projects.promises.model.friend.FriendOwnerity;
import pl.honestit.projects.promises.model.promise.Deadline;
import pl.honestit.projects.promises.model.promise.Promise;
import pl.honestit.projects.promises.model.user.User;

import java.time.Clock;
import java.time.ZonedDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirstPromiseService {

    private final PromiseMaker promiseMaker;
    private final UserManager userManager;
    private final Clock clock;

    @Transactional
    public User makeFirstPromise(FirstPromiseCommand command) {
        log.debug("Command to create first promise: {}", command);

        Promise firstPromise = promiseFromCommand(command);

        UserEntity userEntity = userManager.registerWithFirstPromise(command.getWho());
        log.debug("New user was created: {}", userEntity);

        log.debug("Prmise to make: {}", firstPromise);
        promiseMaker.make(firstPromise);
        log.debug("Promise was give to maker!");

        userFromEntity(userEntity);
        return null;
    }

    private User userFromEntity(UserEntity userEntity) {
        return User.builder()
                .username(userEntity.getUsername())
                .build();
    }

    private Promise promiseFromCommand(FirstPromiseCommand command) {
        return Promise.builder()
                .who(command.getWho())
                .what(command.getWhat())
                .whom(Friend.builder()
                        .name(command.getWhom())
                        .ownerity(FriendOwnerity.builder()
                                .username(command.getWho())
                                .build())
                        .build())
                .when(ZonedDateTime.now(clock))
                .till(Deadline.builder()
                        .zoneId(clock.getZone())
                        .tillDate(command.getTillDate())
                        .tillTime(command.getTillTime())
                        .build())
                .build();
    }


}
