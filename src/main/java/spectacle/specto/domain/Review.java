package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spectacle.specto.domain.enumType.Satisfaction;

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
    private String contents;

    @Column(nullable = false)
    private Integer views;

    private String impression;

    private String bearInMind;
}
