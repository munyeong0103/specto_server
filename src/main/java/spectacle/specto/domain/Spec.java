package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.*;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.req.SpecUpdateReq;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "spec")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spec_id")
    private Long id;

    @Column(nullable = false)
    private Category category;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private String contents;

    @Column
    private String summary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "spec", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "spec", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<Contest> contests = new HashSet<>();

    @OneToMany(mappedBy = "spec", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<Certification> certifications = new HashSet<>();

    @OneToMany(mappedBy = "spec", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<Internship> internships = new HashSet<>();

    @OneToMany(mappedBy = "spec", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "spec", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final Set<Project> projects = new HashSet<>();

    @Builder
    public Spec(Category category, String name, LocalDate startDate, LocalDate endDate,
                boolean completed, String contents, String summary, User user) {
        this.category = category;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completed = completed;
        this.contents = contents;
        this.summary = summary;
        this.user = user;
    }

    public void SpecPrivateUpdate(SpecUpdateReq dto) {
        this.name = dto.getName();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.contents = dto.getContents();
    }
}
