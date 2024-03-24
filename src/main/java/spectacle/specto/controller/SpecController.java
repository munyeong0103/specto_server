package spectacle.specto.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spectacle.specto.dto.SpecPostReq;

@RestController
@RequestMapping("/spec")
public class SpecController {

    // 스펙 조회 - 최근 등록순 정렬
    @GetMapping("/recent")
    public ResponseEntity<?> getSpecByRecent(@RequestParam String category, Pageable pageable) {

        return ResponseEntity.ok(HttpStatus.OK);
    }

    // 스펙 조회 - 오래된순 정렬
    @GetMapping("/oldest")
    public ResponseEntity<?> getSpecByOldest(@RequestParam String category, Pageable pageable) {

        return ResponseEntity.ok(HttpStatus.OK);
    }

    // 스펙 조회 - 조회순 정렬
    @GetMapping("/most-reviewed")
    public ResponseEntity<?> getSpecByMostViewed(@RequestParam String category, Pageable pageable) {

        return ResponseEntity.ok(HttpStatus.OK);
    }

    // 스펙 상세 조회
    @GetMapping("/{specId}")
    public ResponseEntity<?> getSpecDetail(@PathVariable Long specId, @RequestParam String category, Pageable pageable) {

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createSpec(@RequestBody SpecPostReq specPostReq) {

        return ResponseEntity.ok(HttpStatus.OK);
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
