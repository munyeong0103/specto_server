package spectacle.specto.dto.specDto.req;

import lombok.Getter;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.common.Detail;

import java.time.LocalDate;

@Getter
public class SpecPostReq {
    private String name;
    private Category category;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contents;
    private Detail detail;

    public Spec toEntity() {
        return Spec.builder()
                .name(name)
                .category(category)
                .startDate(startDate)
                .endDate(endDate)
                .contents(contents)
                .build();
    }
}
