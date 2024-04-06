package spectacle.specto.dto.reviewDto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import spectacle.specto.domain.Review;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.enumType.Category;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReviewRes {
    private Long reviewId;
    private Long specId;
    private String specName;
    private Category category;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean completed;
    private LocalDate date;
    private Long dPlusDay;

    public static ReviewRes fromEntity(Review review) {
        return ReviewRes.builder()
                .reviewId(review.getId())
                .specId(review.getSpec().getId())
                .specName(review.getSpec().getName())
                .category(review.getSpec().getCategory())
                .startDate(review.getSpec().getStartDate())
                .endDate(review.getSpec().getEndDate())
                .completed(review.getSpec().isCompleted())
                .date(review.getDate())
                .build();
    }
}
