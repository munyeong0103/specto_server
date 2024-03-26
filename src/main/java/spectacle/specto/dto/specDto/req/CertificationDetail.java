package spectacle.specto.dto.specDto.req;

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
}
