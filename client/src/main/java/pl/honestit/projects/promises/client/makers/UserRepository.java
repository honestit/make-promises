package pl.honestit.projects.promises.client.makers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);
}
