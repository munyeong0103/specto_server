package spectacle.specto.service;

import org.springframework.data.domain.Pageable;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.req.SpecUpdateReq;
import spectacle.specto.dto.specDto.res.SpecDetailRes;
import spectacle.specto.dto.specDto.req.SpecPostReq;
import spectacle.specto.dto.specDto.res.SpecRes;

import java.util.List;

public interface SpecService {
    List<SpecRes> getSpecByRecent(Category category, Pageable pageable);

    List<SpecRes> getSpecByOldest(Category category, Pageable pageable);

    List<SpecRes> getSpecByMostViewed(Category category, Pageable pageable);

    SpecDetailRes getSpecDetail(Long specId, Pageable pageable);

    Long createSpec(SpecPostReq specPostReq);

    Long updateSpec(Long specId, SpecUpdateReq specUpdateReq);

    void deleteSpec(Long specId);

    void setSpecCompleted(Spec spec);
}
