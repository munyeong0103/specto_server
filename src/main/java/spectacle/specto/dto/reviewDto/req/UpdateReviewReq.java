package spectacle.specto.dto.reviewDto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spectacle.specto.domain.enumType.Satisfaction;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewReq {

    private Satisfaction satisfaction;

    private Integer progress;

    private String impression;

    private String bearInMind;

    private boolean completed;
}
