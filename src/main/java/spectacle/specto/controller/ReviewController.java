package spectacle.specto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spectacle.specto.dto.reviewDto.req.ReviewDto;
import spectacle.specto.service.ReviewService;

import java.time.LocalDate;

@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    @PostMapping("")
    public ResponseEntity<?> addReview (@RequestBody @Valid ReviewDto reviewDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String)authentication.getCredentials(); //email

        reviewService.postReview(reviewDto, userId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/calendar")
    public ResponseEntity<?> getReviewByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok().body(reviewService.getReviewByCalendarAndDate(date));
    }

    @GetMapping("/calendar/progress")
    public ResponseEntity<?> getReviewByProgress(@RequestParam("year") int year, @RequestParam("month") int month){
        return ResponseEntity.ok().body(reviewService.getReviewByCalendarAndProgress(year, month));
    }

    @GetMapping("/spec/recent/{spec_id}")
    public ResponseEntity<?> getReviewByRecent(@PathVariable("spec_id") Long specId){
        return ResponseEntity.ok().body(reviewService.getReviewBySpecSortedByRecent(specId));
    }

    @GetMapping("/spec/oldest/{spec_id}")
    public ResponseEntity<?> getReviewByOldest(@PathVariable("spec_id") Long specId){
        return ResponseEntity.ok().body(reviewService.getReviewBySpecSortedByOldest(specId));
    }

    @GetMapping("/spec/most/{spec_id}")
    public ResponseEntity<?> getReviewByMost(@PathVariable("spec_id") Long specId){
        return ResponseEntity.ok().body(reviewService.getReviewBySpecSortedByMostViews(specId));
    }

}