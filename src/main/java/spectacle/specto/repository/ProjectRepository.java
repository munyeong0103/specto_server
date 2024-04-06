package spectacle.specto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findProjectBySpecId(Long specId);
}
