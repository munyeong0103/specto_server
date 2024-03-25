package spectacle.specto.dto.specDto.req;

import spectacle.specto.domain.Contest;
import spectacle.specto.domain.enumType.Field;

import java.time.LocalDate;

public class ContestDetail extends Detail {
//    private String host;
//    private Field field;
//    boolean awardStatus;
//    String awardTitle;
//    LocalDate date;

    public Contest toEntity() {
        return Contest.builder()
                .host(super.getHost())
                .field(super.getField())
                .awardStatus(super.isAwardStatus())
                .awardTitle(super.getAwardTitle())
                .date(super.getDate())
                .documentation(super.getDocumentation())
                .build();
    }
}
