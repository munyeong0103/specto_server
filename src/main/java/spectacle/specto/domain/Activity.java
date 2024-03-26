package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.*;
import spectacle.specto.domain.enumType.Field;

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
    public Activity(String host, Field field, byte[] documentation, String motivation, String goal, String direction) {
        this.host = host;
        this.field = field;
        this.documentation = documentation;
        this.motivation = motivation;
        this.goal = goal;
        this.direction = direction;
    }
}
