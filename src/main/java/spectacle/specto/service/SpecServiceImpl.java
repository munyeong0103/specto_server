package spectacle.specto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spectacle.specto.domain.*;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.common.*;
import spectacle.specto.dto.specDto.req.*;
import spectacle.specto.dto.specDto.res.SpecDetailRes;
import spectacle.specto.dto.specDto.res.SpecRes;
import spectacle.specto.repository.*;

import java.time.LocalDate;

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
    public Page<SpecRes> getSpecList(Category category, String sortType, Pageable pageable) {
        return specRepository.selectSpecList(category, sortType, pageable);
    }

    @Override
    public SpecDetailRes<? extends Detail> getSpecDetail(Long specId) {
        Spec spec = this.findSpecBySpecId(specId);
        Category category = spec.getCategory();

        SpecDetailRes<Detail> res = SpecDetailRes.builder()
                .name(spec.getName())
                .category(spec.getCategory().toString())
                .startDate(spec.getStartDate())
                .endDate(spec.getEndDate())
                .completed(spec.isCompleted())
                .contents(spec.getContents())
                .summary(spec.getSummary())
                .build();

        switch (category) {
            case ACTIVITY:
                Activity activity = activityRepository.findActivityBySpecId(specId);
                ActivityDetail activityDetail = ActivityDetail.builder()
                        .host(activity.getHost())
                        .field(activity.getField())
                        .motivation(activity.getMotivation())
                        .goal(activity.getGoal())
                        .direction(activity.getDirection())
                        .build();
                res.addDetail(activityDetail);
                break;
            case CERTIFICATION:
                Certification certification = certificationRepository.findCertificationBySpecId(specId);
                CertificationDetail certificationDetail = CertificationDetail.builder()
                        .host(certification.getHost())
                        .field(certification.getField())
                        .date(certification.getDate())
                        .build();
                res.addDetail(certificationDetail);
                break;
            case CONTEST:
                Contest contest = contestRepository.findContestBySpecId(specId);
                ContestDetail contestDetail = ContestDetail.builder()
                        .host(contest.getHost())
                        .field(contest.getField())
                        .awardStatus(contest.getAwardStatus())
                        .awardTitle(contest.getAwardTitle())
                        .date(contest.getDate())
                        .build();
                res.addDetail(contestDetail);
                break;
            case INTERNSHIP:
                Internship internship = internshipRepository.findInternshipBySpecId(specId);
                InternshipDetail internshipDetail = InternshipDetail.builder()
                        .company(internship.getCompany())
                        .work(internship.getWork())
                        .motivation(internship.getMotivation())
                        .goal(internship.getGoal())
                        .project(internship.getProject())
                        .build();
                res.addDetail(internshipDetail);
                break;
            case PROJECT:
                Project project = projectRepository.findProjectBySpecId(specId);
                ProjectDetail projectDetail = ProjectDetail.builder()
                        .host(project.getHost())
                        .field(project.getField())
                        .motivation(project.getMotivation())
                        .goal(project.getGoal())
                        .direction(project.getDirection())
                        .build();
                res.addDetail(projectDetail);
                break;
        }

        return res;

    }

    @Transactional
    @Override
    public Long createSpec(SpecPostReq specPostReq) {
        User user = userService.getUser();

        Spec spec = specPostReq.toEntity();
        spec.setUser(user);
        Spec newSpec = specRepository.save(spec);
        this.setSpecCompleted(newSpec);

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

    @Transactional
    @Override
    public Long updateSpec(Long specId, SpecUpdateReq specUpdateReq) {
        User user = userService.getUser();
        Spec spec = this.findSpecBySpecId(specId);

        if (!user.getId().equals(spec.getUser().getId())) {
            throw new RuntimeException("해당 스펙의 수정 권한이 없는 사용자입니다.");
        }

        spec.SpecPrivateUpdate(specUpdateReq);
        this.setSpecCompleted(spec);

        Category category = spec.getCategory();
        Detail detail = specUpdateReq.getDetail();

        switch (category) {
            case ACTIVITY:
                Activity activity = activityRepository.findActivityBySpecId(specId);
                activity.ActivityPrivateUpdate(detail);
                break;
            case CERTIFICATION:
                Certification certification = certificationRepository.findCertificationBySpecId(specId);
                certification.certificationPrivateUpdate(detail);
                break;
            case CONTEST:
                Contest contest = contestRepository.findContestBySpecId(specId);
                contest.contestPrivateUpdate(detail);
                break;
            case INTERNSHIP:
                Internship internship = internshipRepository.findInternshipBySpecId(specId);
                internship.internshipPrivateUpdate(detail);
                break;
            case PROJECT:
                Project project = projectRepository.findProjectBySpecId(specId);
                project.projectPrivateUpdate(detail);
                break;
        }

        return spec.getId();
    }

    @Transactional
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

    @Override
    public void setSpecCompleted(Spec spec) {
        spec.SpecUpdateCompleted(LocalDate.now().isAfter(spec.getEndDate()));
    }

    private SpecRes specToSpecRes(Spec spec) {
        return SpecRes.builder()
                .specId(spec.getId())
                .name(spec.getName())
                .category(spec.getCategory())
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
