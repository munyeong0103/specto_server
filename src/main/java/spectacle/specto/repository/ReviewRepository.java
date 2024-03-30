package spectacle.specto.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spectacle.specto.domain.Review;
import spectacle.specto.domain.Spec;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByDate(LocalDate date);

    Slice<Review> findBySpec(Spec spec, Pageable pageable);

}