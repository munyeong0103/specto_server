package spectacle.specto.dto.reviewDto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import spectacle.specto.domain.Review;
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
    private LocalDate date;
    private Long dPlusDay;

    public static ReviewRes fromEntity(Review review) {
        return ReviewRes.builder()
                .reviewId(review.getId())
                .specId(review.getSpec().getId())
                .specName(review.getSpec().getName())
                .category(review.getSpec().getCategory())
                .date(review.getDate())
                .build();
    }
}
