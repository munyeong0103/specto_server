package spectacle.specto.dto.reviewDto.req;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import spectacle.specto.domain.Review;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.User;
import spectacle.specto.domain.enumType.Satisfaction;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long specId;

    private Satisfaction satisfaction;

    private Integer progress;

    private String impression;

    private String bearInMind;

    private LocalDate date;

    public Review toEntity(ReviewDto reviewDto, Spec spec) {
        return Review.builder()
                .spec(spec)
                .satisfaction(reviewDto.satisfaction)
                .progress(reviewDto.progress)
                .impression(reviewDto.impression)
                .bearInMind(reviewDto.bearInMind)
                .date(reviewDto.date)
                .views(0)
                .build();
    }
}
