package spectacle.specto.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spectacle.specto.dto.reviewDto.req.ReviewDto;
import spectacle.specto.dto.reviewDto.res.ReviewRes;
import spectacle.specto.service.ReviewService;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.YearMonth;

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
    public ResponseEntity<?> getReviewByRecent(@PathVariable("spec_id") Long specId, @RequestParam("page") int page){
        return ResponseEntity.ok().body(reviewService.getReviewBySpecSortedByRecent(specId, page).getContent());
    }

}