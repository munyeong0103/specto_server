package spectacle.specto.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.enumType.Category;

import java.util.List;

@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {
    List<Spec> findByCategoryOrderByStartDateAsc(Category category, Pageable pageable);
    List<Spec> findByCategoryOrderByStartDateDesc(Category category, Pageable pageable);

    @Query("SELECT s FROM Spec s JOIN Review r WHERE s.category = :category GROUP BY s.id ORDER BY SUM(r.views) DESC")
    List<Spec> findSpecByMostViewed(Category category, Pageable pageable);
}
