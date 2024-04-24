package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spectacle.specto.domain.enumType.Satisfaction;
import spectacle.specto.dto.reviewDto.req.UpdateReviewReq;

import java.time.LocalDate;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private Satisfaction satisfaction;

    @Column(nullable = false)
    private Integer progress;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer views;

    private String impression;

    private String bearInMind;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Builder
    public Review(Spec spec, Satisfaction satisfaction, Integer progress, LocalDate date, String impression, String bearInMind, Integer views) {
        this.spec = spec;
        this.satisfaction = satisfaction;
        this.progress = progress;
        this.date = date;
        this.impression = impression;
        this.bearInMind = bearInMind;
        this.views = views;
    }

    public void updateViews(Integer views) {
        this.views = views;
    }

    public boolean updateReview(UpdateReviewReq updateReviewReq) {
        this.satisfaction = updateReviewReq.getSatisfaction();
        this.progress = updateReviewReq.getProgress();
        this.impression = updateReviewReq.getImpression();
        this.bearInMind = updateReviewReq.getBearInMind();

        return true;
    }

}