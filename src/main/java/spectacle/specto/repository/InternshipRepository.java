package spectacle.specto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.Internship;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {
}
