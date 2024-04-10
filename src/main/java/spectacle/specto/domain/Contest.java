package spectacle.specto.domain;

import jakarta.persistence.*;
import lombok.*;
import spectacle.specto.domain.enumType.Field;
import spectacle.specto.dto.specDto.common.ContestDetail;
import spectacle.specto.dto.specDto.common.Detail;

import java.time.LocalDate;

@Entity
@Table(name = "contest")
@Getter
@Setter
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

    private String documentation;

    private Boolean awardStatus;

    private String awardTitle;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "spec_id", nullable = false)
    private Spec spec;

    @Builder
    public Contest(String host, Field field, String documentation, Boolean awardStatus, String awardTitle, LocalDate date) {
        this.host = host;
        this.field = field;
        this.documentation = documentation;
        this.awardStatus = awardStatus;
        this.awardTitle = awardTitle;
        this.date = date;
    }

    public void contestPrivateUpdate(Detail detail) {
        this.host = detail.getHost();
        this.field = detail.getField();
        this.awardStatus = detail.isAwardStatus();
        this.awardTitle = detail.getAwardTitle();
        this.date = detail.getDate();
    }
}
