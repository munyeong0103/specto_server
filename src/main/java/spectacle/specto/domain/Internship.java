package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "internship")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internship_id")
    private Long id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String work;

    @Lob
    @Column(length = 1000)
    private byte[] documentation;

    private String motivation;

    private String goal;

    private String project;
}
