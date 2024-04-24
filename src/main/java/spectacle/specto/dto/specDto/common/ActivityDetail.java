package spectacle.specto.dto.specDto.common;

import lombok.Builder;
import lombok.Getter;
import spectacle.specto.domain.enumType.Field;

@Getter
public class ActivityDetail extends Detail {
    private String host;
    private Field field;
    private String motivation;
    private String goal;
    private String direction;
    private String documentation;

    @Builder
    public ActivityDetail(String host, Field field, String motivation, String goal, String direction) {
        this.host = host;
        this.field = field;
        this.motivation = motivation;
        this.goal = goal;
        this.direction = direction;
    }
}
