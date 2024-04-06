package spectacle.specto.dto.specDto.common;

import lombok.Builder;
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

    @Builder
    public InternshipDetail(String company, String work, String motivation, String goal, String project) {
        this.company = company;
        this.work = work;
        this.motivation = motivation;
        this.goal = goal;
        this.project = project;
    }
}
