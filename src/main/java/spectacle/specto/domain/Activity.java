package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.*;
import spectacle.specto.domain.enumType.Field;
import spectacle.specto.dto.specDto.common.ActivityDetail;
import spectacle.specto.dto.specDto.common.Detail;

@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private Field field;

    private String documentation;

    private String motivation;

    private String goal;

    private String direction;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Builder
    public Activity(String host, Field field, String documentation, String motivation, String goal, String direction) {
        this.host = host;
        this.field = field;
        this.documentation = documentation;
        this.motivation = motivation;
        this.goal = goal;
        this.direction = direction;
    }

    public void ActivityPrivateUpdate(Detail detail) {
        this.host = detail.getHost();
        this.field = detail.getField();
        this.motivation = detail.getMotivation();
        this.goal = detail.getGoal();
        this.direction = detail.getDirection();
    }
}
