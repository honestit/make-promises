package pl.honestit.projects.promises.maker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.honestit.projects.promises.maker.model.PromiseEntity;

public interface PromiseEntityRepository extends JpaRepository<PromiseEntity, Long> {
}
