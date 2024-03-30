package spectacle.specto.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spectacle.specto.domain.Review;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.User;
import spectacle.specto.dto.reviewDto.req.ReviewDto;
import spectacle.specto.dto.reviewDto.res.ReviewProgress;
import spectacle.specto.dto.reviewDto.res.ReviewRes;
import spectacle.specto.repository.ReviewRepository;
import spectacle.specto.repository.SpecRepository;
import spectacle.specto.repository.UserRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final SpecRepository specRepository;

    @Override
    public void postReview(ReviewDto reviewDto, String user_id) {
        Optional<User> user = userRepository.findByEmail(user_id);
        Spec spec = specRepository.findById(reviewDto.getSpecId()).orElseThrow();
        Review review = reviewRepository.save(reviewDto.toEntity(reviewDto, spec));
    }

    @Override
    public List<ReviewRes> getReviewByCalendarAndDate(LocalDate date){
        List<Review> reviews = reviewRepository.findByDate(date);
        List<ReviewRes> reviewResList = new ArrayList<>();

        for (Review review : reviews) {
            ReviewRes reviewRes = ReviewRes.fromEntity(review);
            //d-day 계산
            LocalDate currentDate = LocalDate.now();
            long betweenDays = ChronoUnit.DAYS.between(date, currentDate);
            reviewRes.setDPlusDay(betweenDays);

            reviewResList.add(reviewRes);
        }
        return reviewResList;
    }

    @Override
    public List<ReviewProgress> getReviewByCalendarAndProgress(int year, int month){
        // 날짜 설정
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        List<LocalDate> dateRange = startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());

        List<ReviewProgress> reviewProgresses = new ArrayList<>();
        Integer progressAverage = 0;

        for (LocalDate date : dateRange) {
            ReviewProgress reviewProgress = new ReviewProgress();
            List<Review> reviews = reviewRepository.findByDate(date);
            if (reviews.isEmpty()) {
                progressAverage = 0;
            }
            else { // 평균 progress 구함
                for (Review review : reviews) {
                    progressAverage+=review.getProgress();
                }
                progressAverage /= reviews.size();
            }

            reviewProgress.setProgress(progressAverage);
            reviewProgress.setDate(date);
            reviewProgresses.add(reviewProgress);

            progressAverage = 0;
        }
        return reviewProgresses;
    }

    @Override
    public Slice<ReviewRes> getReviewBySpecSortedByRecent(long specId, int page) {
        Spec spec = specRepository.findById(specId).orElseThrow();

        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Slice<Review> reviews = reviewRepository.findBySpec(spec, pageable);

        return reviews.map(ReviewRes::fromEntity);
    }
}

