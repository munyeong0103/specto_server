package spectacle.specto.dto.specDto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.enumType.Category;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class SpecRes {
    private Long specId;
    private String name;
    private Category category;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean completed;
    private Long reviewCnt;

    public SpecRes(Spec spec, Long reviewCnt) {
        this.specId = spec.getId();
        this.name = spec.getName();
        this.category = spec.getCategory();
        this.startDate = spec.getStartDate();
        this.endDate = spec.getEndDate();
        this.completed = spec.isCompleted();
        this.reviewCnt = reviewCnt;
    }
}
