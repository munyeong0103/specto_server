package spectacle.specto.dto.reviewDto.res;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReviewProgress {
    private LocalDate date;

    private Integer progress;
}
