package spectacle.specto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
}
