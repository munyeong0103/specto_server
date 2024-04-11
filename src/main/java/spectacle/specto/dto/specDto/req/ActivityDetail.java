package spectacle.specto.dto.specDto.req;

import lombok.Getter;
import spectacle.specto.domain.Activity;
import spectacle.specto.domain.Project;
import spectacle.specto.domain.enumType.Field;

@Getter
public class ActivityDetail extends Detail {
    private String host;
    private Field field;
    private String motivation;
    private String goal;
    private String direction;
    private byte[] documentation;

    public Activity toEntity() {
        return Activity.builder()
                .host(host)
                .field(field)
                .motivation(motivation)
                .goal(goal)
                .direction(direction)
                .documentation(documentation)
                .build();
    }
}
