package spectacle.specto.dto.specDto.res;

import lombok.Builder;
import spectacle.specto.dto.specDto.common.Detail;

import java.time.LocalDate;

public class SpecDetailRes {
    private String name;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean completed;
    private String contents;
    private String summary;
    private Detail detail;

    @Builder
    public SpecDetailRes(String name, String category, LocalDate startDate, LocalDate endDate, boolean completed, String contents, String summary, Detail detail) {
        this.name = name;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completed = completed;
        this.contents = contents;
        this.summary = summary;
        this.detail = detail;
    }
}
