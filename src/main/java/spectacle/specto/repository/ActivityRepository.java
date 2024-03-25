package spectacle.specto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
