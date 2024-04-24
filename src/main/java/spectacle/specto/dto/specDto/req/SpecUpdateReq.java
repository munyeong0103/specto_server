package spectacle.specto.dto.specDto.req;

import lombok.Getter;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.common.Detail;

import java.time.LocalDate;

@Getter
public class SpecUpdateReq {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contents;
    private Detail detail;

    public Spec toEntity() {
        return Spec.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .contents(contents)
                .build();
    }
}
