package spectacle.specto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.req.SpecPostReq;
import spectacle.specto.dto.specDto.req.SpecUpdateReq;
import spectacle.specto.dto.specDto.res.SpecDetailRes;
import spectacle.specto.dto.specDto.res.SpecRes;
import spectacle.specto.service.SpecService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spec")
public class SpecController {

    private final SpecService specService;

    // 스펙 조회 - 최근 등록순 정렬
    @GetMapping("/recent")
    public ResponseEntity<?> getSpecByRecent(@RequestParam Category category, Pageable pageable) {
        List<SpecRes> specByRecent = specService.getSpecByRecent(category, pageable);
        return ResponseEntity.ok(specByRecent);
    }

    // 스펙 조회 - 오래된순 정렬
    @GetMapping("/oldest")
    public ResponseEntity<?> getSpecByOldest(@RequestParam Category category, Pageable pageable) {
        List<SpecRes> specByOldest = specService.getSpecByOldest(category, pageable);
        return ResponseEntity.ok(specByOldest);
    }

    // 스펙 조회 - 조회순 정렬
    @GetMapping("/most-reviewed")
    public ResponseEntity<?> getSpecByMostViewed(@RequestParam Category category, Pageable pageable) {
        List<SpecRes> specByMostViewed = specService.getSpecByMostViewed(category, pageable);
        return ResponseEntity.ok(specByMostViewed);
    }

    // 스펙 상세 조회
    @GetMapping("/{specId}")
    public ResponseEntity<?> getSpecDetail(@PathVariable Long specId, Pageable pageable) {
        SpecDetailRes specDetailRes = specService.getSpecDetail(specId, pageable);
        return ResponseEntity.ok(specDetailRes);
    }

    // 스펙 생성
    @PostMapping("")
    public ResponseEntity<?> createSpec(@RequestBody SpecPostReq specPostReq) {
        Long specId = specService.createSpec(specPostReq);
        return ResponseEntity.ok(specId);
    }

    // 스펙 수정
    @PatchMapping("{specId}")
    public ResponseEntity<?> updateSpecInfo(@PathVariable Long specId ,@RequestBody SpecUpdateReq specUpdateReq) {
        Long id = specService.updateSpec(specId, specUpdateReq);
        return ResponseEntity.ok("spec " + id + "수정 성공");
    }

    // 스펙 삭제
    @DeleteMapping("{specId}")
    public ResponseEntity<?> deleteSpec(@PathVariable Long specId) {
        specService.deleteSpec(specId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
