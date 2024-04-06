package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.*;
import spectacle.specto.dto.specDto.common.InternshipDetail;

@Entity
@Table(name = "internship")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Builder
    public Internship(String company, String work, byte[] documentation, String motivation, String goal, String project) {
        this.company = company;
        this.work = work;
        this.documentation = documentation;
        this.motivation = motivation;
        this.goal = goal;
        this.project = project;
    }

    public void internshipPrivateUpdate(InternshipDetail detail) {
        this.company = detail.getCompany();
        this.work = detail.getWork();
        this.motivation = detail.getMotivation();
        this.goal = detail.getGoal();
        this.project = detail.getProject();
    }
}
