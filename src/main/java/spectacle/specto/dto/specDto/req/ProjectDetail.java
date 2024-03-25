package spectacle.specto.dto.specDto.req;

import lombok.Getter;
import spectacle.specto.domain.Project;
import spectacle.specto.domain.enumType.Field;

@Getter
public class ProjectDetail extends Detail {
    private String host;
    private Field field;
    private String motivation;
    private String goal;
    private String direction;

    public Project toEntity() {
        return Project.builder()
                .field(field)
                .motivation(motivation)
                .goal(goal)
                .direction(direction)
                .documentation(super.getDocumentation())
                .build();
    }
}
