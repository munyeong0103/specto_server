package spectacle.specto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spectacle.specto.domain.*;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.req.*;
import spectacle.specto.dto.specDto.res.SpecDetailRes;
import spectacle.specto.dto.specDto.res.SpecRes;
import spectacle.specto.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecServiceImpl implements SpecService{

    private final SpecRepository specRepository;
    private final ActivityRepository activityRepository;
    private final CertificationRepository certificationRepository;
    private final ContestRepository contestRepository;
    private final InternshipRepository internshipRepository;
    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    @Override
    public List<SpecRes> getSpecByRecent(Category category, Pageable pageable) {
        List<Spec> specList = specRepository.findByCategoryOrderByStartDateDesc(category, pageable);
        return specList.stream().map(this::specToSpecRes).toList();
    }

    @Override
    public List<SpecRes> getSpecByOldest(Category category, Pageable pageable) {
        List<Spec> specList = specRepository.findByCategoryOrderByStartDateAsc(category, pageable);
        return specList.stream().map(this::specToSpecRes).toList();
    }

    @Override
    public List<SpecRes> getSpecByMostViewed(Category category, Pageable pageable) {
        return null;
    }

    @Override
    public SpecDetailRes getSpecDetail(Long specId, Category category, Pageable pageable) {
        return null;
    }

    @Override
    public Long createSpec(SpecPostReq specPostReq) {
        Spec spec = specPostReq.toEntity();
        spec.setUser(userRepository.findById((long) 1).orElseThrow(() -> new NullPointerException()));
        Spec newSpec = specRepository.save(spec);

        Category category = specPostReq.getCategory();
        Detail detail = specPostReq.getDetail();

        switch (category) {
            case ACTIVITY:
                Activity activity = ((ActivityDetail) detail).toEntity();
                activity.setSpec(newSpec);
                activityRepository.save(activity);
            case CERTIFICATION:
                Certification certification = ((CertificationDetail) detail).toEntity();
                certification.setSpec(newSpec);
                certificationRepository.save(certification);
            case CONTEST:
                if (detail != null) {
                    ContestDetail contestDetail = (ContestDetail) detail;
                    Contest contest = contestDetail.toEntity();
                    contest.setSpec(newSpec);
                    contestRepository.save(contest);
                }
            case INTERNSHIP:
                Internship internship = ((InternshipDetail) detail).toEntity();
                internship.setSpec(newSpec);
                internshipRepository.save(internship);
            case PROJECT:
                Project project = ((ProjectDetail) detail).toEntity();
                project.setSpec(newSpec);
                projectRepository.save(project);
        }

        return newSpec.getId();
    }

    private SpecRes specToSpecRes(Spec spec) {
        return SpecRes.builder()
                .specId(spec.getId())
                .name(spec.getName())
                .category(spec.getCategory().toString())
                .startDate(spec.getStartDate())
                .endDate(spec.getEndDate())
                .completed(spec.isCompleted())
                .build();
    }
}
