package spectacle.specto.dto.specDto.req;

import spectacle.specto.domain.Contest;
import spectacle.specto.domain.enumType.Field;

import java.time.LocalDate;

public class ContestDetail extends Detail {
    private String host;
    private Field field;
    boolean isAwardStatus;
    String awardTitle;
    LocalDate date;

    public Contest toEntity() {
        return Contest.builder()
                .host(host)
                .field(field)
                .awardStatus(isAwardStatus)
                .awardTitle(awardTitle)
                .date(date)
                .documentation(super.getDocumentation())
                .build();
    }
}
