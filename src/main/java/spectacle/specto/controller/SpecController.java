package spectacle.specto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.common.Detail;
import spectacle.specto.dto.specDto.req.SpecPostReq;
import spectacle.specto.dto.specDto.req.SpecUpdateReq;
import spectacle.specto.dto.specDto.res.SpecDetailRes;
import spectacle.specto.service.SpecService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spec")
public class SpecController {

    private final SpecService specService;

    // 스펙 목록 조회
    @GetMapping("")
    public ResponseEntity<?> getSpecList(@RequestParam(required = false) Category category,
                                         @RequestParam(required = false) String sortType,
                                         @PageableDefault() Pageable pageable) {
        return ResponseEntity.ok(specService.getSpecList(category, sortType, pageable));
    }

    // 스펙 상세 조회
    @GetMapping("/{specId}")
    public ResponseEntity<?> getSpecDetail(@PathVariable Long specId) {
        SpecDetailRes<? extends Detail> specDetailRes = specService.getSpecDetail(specId);
        return ResponseEntity.ok(specDetailRes);
    }

    // 스펙 생성
    @PostMapping("")
    public ResponseEntity<?> createSpec(@RequestBody SpecPostReq specPostReq) {
        Long specId = specService.createSpec(specPostReq);
        return ResponseEntity.ok(specId);
    }

    // 스펙 수정
    @PatchMapping("/{specId}")
    public ResponseEntity<?> updateSpecInfo(@PathVariable Long specId ,@RequestBody SpecUpdateReq specUpdateReq) {
        Long id = specService.updateSpec(specId, specUpdateReq);
        return ResponseEntity.ok("spec " + id + " 수정 성공");
    }

    // 스펙 삭제
    @DeleteMapping("/{specId}")
    public ResponseEntity<?> deleteSpec(@PathVariable Long specId) {
        specService.deleteSpec(specId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}