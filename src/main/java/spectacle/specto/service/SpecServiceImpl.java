package spectacle.specto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spectacle.specto.domain.*;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.common.*;
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
    private final UserService userService;

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
        List<Spec> specList = specRepository.findSpecByMostViewed(category, pageable);
        return specList.stream().map(this::specToSpecRes).toList();
    }

    @Override
    public SpecDetailRes getSpecDetail(Long specId, Pageable pageable) {
        Spec spec = this.findSpecBySpecId(specId);
        Category category = spec.getCategory();
        Detail detail = new Detail();

        switch (category) {
            case ACTIVITY:
                Activity activity = activityRepository.findActivityBySpecId(specId);
                detail = ActivityDetail.builder()
                        .host(activity.getHost())
                        .field(activity.getField())
                        .motivation(activity.getMotivation())
                        .goal(activity.getGoal())
                        .direction(activity.getDirection())
                        .build();
                break;
            case CERTIFICATION:
                Certification certification = certificationRepository.findCertificationBySpecId(specId);
                detail = CertificationDetail.builder()
                        .host(certification.getHost())
                        .field(certification.getField())
                        .date(certification.getDate())
                        .build();
                break;
            case CONTEST:
                Contest contest = contestRepository.findContestBySpecId(specId);
                detail = ContestDetail.builder()
                        .host(contest.getHost())
                        .field(contest.getField())
                        .awardStatus(contest.getAwardStatus())
                        .awardTitle(contest.getAwardTitle())
                        .date(contest.getDate())
                        .build();
            case INTERNSHIP:
                Internship internship = internshipRepository.findInternshipBySpecId(specId);
                detail = InternshipDetail.builder()
                        .company(internship.getCompany())
                        .work(internship.getWork())
                        .motivation(internship.getMotivation())
                        .goal(internship.getGoal())
                        .project(internship.getProject())
                        .build();
                break;
            case PROJECT:
                Project project = projectRepository.findProjectBySpecId(specId);
                detail = ProjectDetail.builder()
                        .host(project.getHost())
                        .field(project.getField())
                        .motivation(project.getMotivation())
                        .goal(project.getGoal())
                        .direction(project.getDirection())
                        .build();
                break;
        }

        return SpecDetailRes.builder()
                .name(spec.getName())
                .category(spec.getCategory().toString())
                .startDate(spec.getStartDate())
                .endDate(spec.getEndDate())
                .completed(spec.isCompleted())
                .contents(spec.getContents())
                .summary(spec.getSummary())
                .detail(detail)
                .build();

    }

    @Override
    public Long createSpec(SpecPostReq specPostReq) {
        User user = userService.getUser();

        Spec spec = specPostReq.toEntity();
        spec.setUser(user);
        Spec newSpec = specRepository.save(spec);

        Category category = specPostReq.getCategory();
        Detail detail = specPostReq.getDetail();

        switch (category) {
            case ACTIVITY:
                Activity activity = detail.toActivity();
                activity.setSpec(newSpec);
                activityRepository.save(activity);
                break;
            case CERTIFICATION:
                Certification certification = detail.toCertification();
                certification.setSpec(newSpec);
                certificationRepository.save(certification);
                break;
            case CONTEST:
                Contest contest = detail.toContest();
                contest.setSpec(newSpec);
                contestRepository.save(contest);
                break;
            case INTERNSHIP:
                Internship internship = detail.toInternship();
                internship.setSpec(newSpec);
                internshipRepository.save(internship);
                break;
            case PROJECT:
                Project project = detail.toProject();
                project.setSpec(newSpec);
                projectRepository.save(project);
                break;
        }

        return newSpec.getId();
    }

    @Override
    public Long updateSpec(Long specId, SpecUpdateReq specUpdateReq) {
        User user = userService.getUser();
        Spec spec = this.findSpecBySpecId(specId);

        if (!user.getId().equals(spec.getUser().getId())) {
            throw new RuntimeException("해당 스펙의 수정 권한이 없는 사용자입니다.");
        }

        spec.SpecPrivateUpdate(specUpdateReq);

        Category category = spec.getCategory();
        Detail detail = specUpdateReq.getDetail();

        switch (category) {
            case ACTIVITY:
                Activity activity = activityRepository.findActivityBySpecId(specId);
                activity.ActivityPrivateUpdate((ActivityDetail) detail);
                break;
            case CERTIFICATION:
                Certification certification = certificationRepository.findCertificationBySpecId(specId);
                certification.certificationPrivateUpdate((CertificationDetail) detail);
                break;
            case CONTEST:
                Contest contest = contestRepository.findContestBySpecId(specId);
                contest.contestPrivateUpdate((ContestDetail) detail);
                break;
            case INTERNSHIP:
                Internship internship = internshipRepository.findInternshipBySpecId(specId);
                internship.internshipPrivateUpdate((InternshipDetail) detail);
                break;
            case PROJECT:
                Project project = projectRepository.findProjectBySpecId(specId);
                project.projectPrivateUpdate((ProjectDetail) detail);
                break;
        }

        return spec.getId();
    }

    @Override
    public void deleteSpec(Long specId) {
        User user = userService.getUser();
        Spec spec = this.findSpecBySpecId(specId);

        if (!user.getId().equals(spec.getUser().getId())) {
            throw new RuntimeException("해당 스펙의 삭제 권한이 없는 사용자입니다.");
        }

        Category category = spec.getCategory();

        switch (category) {
            case ACTIVITY:
                activityRepository.delete(activityRepository.findActivityBySpecId(specId));
                break;
            case CERTIFICATION:
                certificationRepository.delete(certificationRepository.findCertificationBySpecId(specId));
                break;
            case CONTEST:
                contestRepository.delete(contestRepository.findContestBySpecId(specId));
                break;
            case INTERNSHIP:
                internshipRepository.delete(internshipRepository.findInternshipBySpecId(specId));
                break;
            case PROJECT:
                projectRepository.delete(projectRepository.findProjectBySpecId(specId));
                break;
        }

        specRepository.deleteById(specId);
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

    private Spec findSpecBySpecId(Long specId) {
        return specRepository.findById(specId)
                .orElseThrow(() -> new RuntimeException("WRONG_SPEC_ID"));
    }
}
