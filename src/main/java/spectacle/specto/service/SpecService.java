package spectacle.specto.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.common.Detail;
import spectacle.specto.dto.specDto.req.SpecUpdateReq;
import spectacle.specto.dto.specDto.res.SpecDetailRes;
import spectacle.specto.dto.specDto.req.SpecPostReq;
import spectacle.specto.dto.specDto.res.SpecRes;

import java.util.List;

public interface SpecService {
    Page<SpecRes> getSpecList(Category category, String sortType, Pageable pageable);

    SpecDetailRes<? extends Detail> getSpecDetail(Long specId);

    Long createSpec(SpecPostReq specPostReq);

    Long updateSpec(Long specId, SpecUpdateReq specUpdateReq);

    void deleteSpec(Long specId);

    void setSpecCompleted(Spec spec);
}
