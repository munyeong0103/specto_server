package spectacle.specto.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import spectacle.specto.dto.SpecDetailRes;
import spectacle.specto.dto.SpecPostReq;
import spectacle.specto.dto.SpecRes;

import java.util.List;

public interface SpecService {
    List<SpecRes> getSpecByRecent(String category, Pageable pageable);

    List<SpecRes> getSpecByOldest(String category, Pageable pageable);

    List<SpecRes> getSpecByMostViewed(String category, Pageable pageable);

    SpecDetailRes getSpecDetail(Long specId, String category, Pageable pageable);

    Long createSpec(SpecPostReq specPostReq);
}
