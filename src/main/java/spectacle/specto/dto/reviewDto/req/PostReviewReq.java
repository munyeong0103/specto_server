package spectacle.specto.dto.reviewDto.req;

import lombok.*;
import spectacle.specto.domain.Review;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.enumType.Satisfaction;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostReviewReq {

    private Long specId;

    private Satisfaction satisfaction;

    private Integer progress;

    private String impression;

    private String bearInMind;

    private LocalDate date;

    public Review toEntity(PostReviewReq postReviewReq, Spec spec) {
        return Review.builder()
                .spec(spec)
                .satisfaction(postReviewReq.satisfaction)
                .progress(postReviewReq.progress)
                .impression(postReviewReq.impression)
                .bearInMind(postReviewReq.bearInMind)
                .date(postReviewReq.date)
                .views(0)
                .build();
    }
}
