package pl.honestit.projects.promises.client.makers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity registerWithFirstPromise(String username) {
        log.debug("Registering user via first promise: {}", username);

        if (userRepository.existsByUsername(username)) {
            log.debug("User with same username already exists. Breaking register with exception");
            throw new UserAlreadyExistsException(String.format("User %s already exists", username));
        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .securityDetails(UserSecurityDetails.builder()
                        .activateRequired(true)
                        .build())
                .build();

        userRepository.save(user);
        log.debug("User saved in repository");

        return user;
    }


}
