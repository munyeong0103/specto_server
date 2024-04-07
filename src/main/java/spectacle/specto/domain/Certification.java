package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.*;
import spectacle.specto.domain.enumType.Field;
import spectacle.specto.dto.specDto.common.CertificationDetail;

import java.time.LocalDate;

@Entity
@Table(name = "certification")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id")
    private Long id;

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private Field field;

    private String documentation;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Builder
    public Certification(String host, Field field, String documentation, LocalDate date) {
        this.host = host;
        this.field = field;
        this.documentation = documentation;
        this.date = date;
    }

    public void certificationPrivateUpdate(CertificationDetail detail) {
        this.host = detail.getHost();
        this.field = detail.getField();
        this.date = detail.getDate();
    }
}
