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
        return null;
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
