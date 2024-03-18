package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spectacle.specto.domain.enumType.Category;

import java.time.LocalDate;

@Entity
@Table(name = "spec")
@Getter
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



}
