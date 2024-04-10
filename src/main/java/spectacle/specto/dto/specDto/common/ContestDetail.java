package spectacle.specto.dto.specDto.common;

import lombok.Builder;
import lombok.Getter;
import spectacle.specto.domain.enumType.Field;

import java.time.LocalDate;

@Getter
public class ContestDetail extends Detail {
    private String host;
    private Field field;
    boolean awardStatus;
    String awardTitle;
    LocalDate date;

    @Builder
    public ContestDetail(String host, Field field, boolean awardStatus, String awardTitle, LocalDate date) {
        this.host = host;
        this.field = field;
        this.awardStatus = awardStatus;
        this.awardTitle = awardTitle;
        this.date = date;
    }
}