package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spectacle.specto.domain.enumType.Field;

import java.time.LocalDate;

@Entity
@Table(name = "contest")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contest_id")
    private Long id;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private Field field;

    @Lob
    @Column(length = 1000)
    private byte[] documentation;

    private String awardStatus;

    private String awardTitle;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;
}
