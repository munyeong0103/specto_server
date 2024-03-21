package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spectacle.specto.domain.enumType.Field;

@Entity
@Table(name = "project")
@Getter
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
}
