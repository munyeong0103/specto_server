package spectacle.specto.dto.specDto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class SpecRes {
    private Long specId;
    private String name;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean completed;
}
