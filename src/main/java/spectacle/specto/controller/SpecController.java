package spectacle.specto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spectacle.specto.domain.enumType.Category;
import spectacle.specto.dto.specDto.req.SpecPostReq;
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

        return ResponseEntity.ok(HttpStatus.OK);
    }

    // 스펙 상세 조회
    @GetMapping("/{specId}")
    public ResponseEntity<?> getSpecDetail(@PathVariable Long specId, @RequestParam Category category, Pageable pageable) {

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createSpec(@RequestBody SpecPostReq specPostReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String)authentication.getCredentials(); //email

        return ResponseEntity.ok(specService.createSpec(userId, specPostReq));
    }

    @PutMapping("{specId}")
    public ResponseEntity<?> updateSpecInfo(@PathVariable Long specId ,@RequestBody SpecPostReq specPostReq) {

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("{specId}")
    public ResponseEntity<?> deleteSpec(@PathVariable Long specId) {

        return ResponseEntity.ok(HttpStatus.OK);
    }


}
