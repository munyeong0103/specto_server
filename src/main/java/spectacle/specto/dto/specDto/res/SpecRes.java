package spectacle.specto.dto.specDto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class SpecRes {
    Long specId;
    String name;
    String category;
    LocalDate startDate;
    LocalDate endDate;
    boolean completed;
}
