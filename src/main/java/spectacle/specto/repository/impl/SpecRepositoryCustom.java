package spectacle.specto.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.res.SpecRes;

public interface SpecRepositoryCustom {
    Page<SpecRes> selectSpecList(Category category, String sortType, Pageable pageable);

}
