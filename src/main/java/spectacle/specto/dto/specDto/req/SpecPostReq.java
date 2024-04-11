package spectacle.specto.dto.specDto.req;

import lombok.Getter;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.enumType.Category;

import java.time.LocalDate;

@Getter
public class SpecPostReq {
    String name;
    Category category;
    LocalDate startDate;
    LocalDate endDate;
    String contents;
    ActivityDetail detail;

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
