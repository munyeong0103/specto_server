package spectacle.specto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.Contest;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    Contest findContestBySpecId(Long specId);
}
