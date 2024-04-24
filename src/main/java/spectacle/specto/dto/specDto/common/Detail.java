package spectacle.specto.dto.specDto.common;

import lombok.Getter;
import spectacle.specto.domain.*;
import spectacle.specto.domain.enumType.Field;

import java.time.LocalDate;

@Getter
public class Detail {
    private String documentation;

    private String host;
    private Field field;
    private String motivation;
    private String goal;
    private String direction;
    private boolean awardStatus;
    private String awardTitle;
    private LocalDate date;
    private String company;
    private String work;
    private String project;

    public Activity toActivity() {
        return Activity.builder()
                .host(host)
                .field(field)
                .motivation(motivation)
                .goal(goal)
                .direction(direction)
                .documentation(documentation)
                .build();
    }

    public Certification toCertification() {
        return Certification.builder()
                .host(host)
                .field(field)
                .date(date)
                .documentation(documentation)
                .build();
    }

    public Contest toContest() {
        return Contest.builder()
                .host(host)
                .field(field)
                .awardStatus(awardStatus)
                .awardTitle(awardTitle)
                .date(date)
                .documentation(documentation)
                .build();
    }

    public Internship toInternship() {
        return Internship.builder()
                .company(company)
                .work(work)
                .motivation(motivation)
                .goal(goal)
                .project(project)
                .documentation(documentation)
                .build();
    }

    public Project toProject() {
        return Project.builder()
                .field(field)
                .motivation(motivation)
                .goal(goal)
                .direction(direction)
                .documentation(documentation)
                .build();
    }
}
