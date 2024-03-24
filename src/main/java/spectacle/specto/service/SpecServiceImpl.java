package spectacle.specto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spectacle.specto.domain.Spec;
import spectacle.specto.dto.SpecDetailRes;
import spectacle.specto.dto.SpecPostReq;
import spectacle.specto.dto.SpecRes;
import spectacle.specto.repository.SpecRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecServiceImpl implements SpecService{

    private final SpecRepository specRepository;

    @Override
    public List<SpecRes> getSpecByRecent(String category, Pageable pageable) {
        return null;
    }

    @Override
    public List<SpecRes> getSpecByOldest(String category, Pageable pageable) {
        return null;
    }

    @Override
    public List<SpecRes> getSpecByMostViewed(String category, Pageable pageable) {
        return null;
    }

    @Override
    public SpecDetailRes getSpecDetail(Long specId, String category, Pageable pageable) {
        return null;
    }

    @Override
    public Long createSpec(SpecPostReq specPostReq) {
        return null;
    }
}
