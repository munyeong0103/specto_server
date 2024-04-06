package spectacle.specto.dto.specDto.common;

import lombok.Builder;
import lombok.Getter;
import spectacle.specto.domain.Certification;
import spectacle.specto.domain.enumType.Field;

import java.time.LocalDate;

@Getter
public class CertificationDetail extends Detail {
    private String host;
    private Field field;
    private LocalDate date;

    public Certification toEntity() {
        return Certification.builder()
                .host(host)
                .field(field)
                .date(date)
                .documentation(super.getDocumentation())
                .build();
    }

    @Builder
    public CertificationDetail(String host, Field field, LocalDate date) {
        this.host = host;
        this.field = field;
        this.date = date;
    }
}
