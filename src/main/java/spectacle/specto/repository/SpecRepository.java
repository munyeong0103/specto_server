package spectacle.specto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.Spec;
import spectacle.specto.repository.impl.SpecRepositoryCustom;

@Repository
public interface SpecRepository extends JpaRepository<Spec, Long>, SpecRepositoryCustom {
}
