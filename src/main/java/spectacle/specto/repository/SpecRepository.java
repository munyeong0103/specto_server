package spectacle.specto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.Spec;

import java.util.List;

@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {
}
