package spectacle.specto.dto.reviewDto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import spectacle.specto.domain.Review;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.domain.enumType.Satisfaction;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReviewDetail {
    private String specName;
    private Category category;
    private Long dPlusDay;
    private Satisfaction satisfaction;
    private Integer progress;
    private String impression;
    private String bearInMind;
    private LocalDate date;

    public static ReviewDetail fromEntity(Review review) {
        return ReviewDetail.builder()
                .specName(review.getSpec().getName())
                .category(review.getSpec().getCategory())
                .satisfaction(review.getSatisfaction())
                .progress(review.getProgress())
                .impression(review.getImpression())
                .bearInMind(review.getBearInMind())
                .date(review.getDate())
                .build();
    }
}