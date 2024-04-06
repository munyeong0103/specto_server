package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.*;
import spectacle.specto.domain.enumType.Field;
import spectacle.specto.dto.specDto.common.ProjectDetail;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private Field field;

    @Lob
    @Column(nullable = false, length = 1000)
    private byte[] documentation;

    private String motivation;

    private String goal;

    private String direction;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Builder
    public Project(String host, Field field, byte[] documentation, String motivation, String goal, String direction) {
        this.host = host;
        this.field = field;
        this.documentation = documentation;
        this.motivation = motivation;
        this.goal = goal;
        this.direction = direction;
    }

    public void projectPrivateUpdate(ProjectDetail detail) {
        this.host = detail.getHost();
        this.field = detail.getField();
        this.motivation = detail.getMotivation();
        this.goal = detail.getGoal();
        this.direction = detail.getDirection();
    }
}
