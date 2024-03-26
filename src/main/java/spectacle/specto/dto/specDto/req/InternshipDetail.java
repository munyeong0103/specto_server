package spectacle.specto.dto.specDto.req;

import lombok.Getter;
import spectacle.specto.domain.Internship;

@Getter
public class InternshipDetail extends Detail {
    private String company;
    private String work;
    private String motivation;
    private String goal;
    private String project;

    public Internship toEntity() {
        return Internship.builder()
                .company(company)
                .work(work)
                .goal(goal)
                .project(project)
                .documentation(super.getDocumentation())
                .build();
    }
}
