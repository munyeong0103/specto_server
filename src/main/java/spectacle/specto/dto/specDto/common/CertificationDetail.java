package spectacle.specto.dto.specDto.common;

import lombok.Builder;
import lombok.Getter;
import spectacle.specto.domain.enumType.Field;

import java.time.LocalDate;

@Getter
public class CertificationDetail extends Detail {
    private String host;
    private Field field;
    private LocalDate date;

    @Builder
    public CertificationDetail(String host, Field field, LocalDate date) {
        this.host = host;
        this.field = field;
        this.date = date;
    }
}
