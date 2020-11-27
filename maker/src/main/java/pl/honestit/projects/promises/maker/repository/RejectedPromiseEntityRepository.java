package pl.honestit.projects.promises.maker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.honestit.projects.promises.maker.model.RejectedPromiseEntity;

public interface RejectedPromiseEntityRepository extends JpaRepository<RejectedPromiseEntity, Long> {
}
