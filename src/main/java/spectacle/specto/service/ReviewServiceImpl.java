package spectacle.specto.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spectacle.specto.domain.Review;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.User;
import spectacle.specto.dto.reviewDto.req.PostReviewReq;
import spectacle.specto.dto.reviewDto.req.UpdateReviewReq;
import spectacle.specto.dto.reviewDto.res.ReviewDetail;
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

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final SpecRepository specRepository;

    @Override
    public String postReview(PostReviewReq postReviewReq, String user_id) {
        Optional<User> user = userRepository.findByEmail(user_id);
        Spec spec = specRepository.findById(postReviewReq.getSpecId()).orElseThrow();

        if (postReviewReq.getDate().isBefore(spec.getStartDate()) || postReviewReq.getDate().isAfter(spec.getEndDate())) {
            return ("등록한 스펙 기간 사이에 회고를 추가해주세요");
        }
        else {
            Review review = reviewRepository.save(postReviewReq.toEntity(postReviewReq, spec));
            return ("회고가 등록되었습니다");
        }
    }

    @Override
    public List<ReviewRes> getReviewByCalendarAndDate(LocalDate date){
        List<Review> reviews = reviewRepository.findByDate(date);

        return addDPlusDay(reviews);
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
    public List<ReviewRes> getReviewBySpecSortedByRecent(long specId) {
        Spec spec = specRepository.findById(specId).orElseThrow();

        List<Review> reviews = reviewRepository.findBySpecOrderByIdDesc(spec);
        isCompleted(reviews, spec);

        return addDPlusDay(reviews);
    }


    @Override
    public List<ReviewRes> getReviewBySpecSortedByOldest(long specId) {
        Spec spec = specRepository.findById(specId).orElseThrow();

        List<Review> reviews = reviewRepository.findBySpecOrderByIdAsc(spec);
        isCompleted(reviews, spec);

        return addDPlusDay(reviews);
    }

    @Override
    public List<ReviewRes> getReviewBySpecSortedByMostViews(long specId) {
        Spec spec = specRepository.findById(specId).orElseThrow();

        List<Review> reviews = reviewRepository.findBySpecOrderByViewsDesc(spec);
        isCompleted(reviews, spec);

        return addDPlusDay(reviews);
    }

    @Override
    public ReviewDetail getReviewDetail(long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        //조회수 변경
        review.updateViews(review.getViews()+1);
        reviewRepository.save(review);

        //d-day 설정
        ReviewDetail reviewDetail = ReviewDetail.fromEntity(review);
        LocalDate currentDate = LocalDate.now();
        long betweenDays = ChronoUnit.DAYS.between(review.getDate(), currentDate);
        reviewDetail.setDPlusDay(betweenDays);

        return reviewDetail;
    }

    @Override
    public boolean updateReview(UpdateReviewReq updateReviewReq, long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        boolean updated = review.updateReview(updateReviewReq);

        if (updated) {  return true;    }
        else {  return false;   }
    }

    @Override
    public boolean deleteReview(long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        reviewRepository.deleteById(reviewId);
        return true;
    }


    @Override
    public List<ReviewRes> addDPlusDay(List<Review> reviews) {

        List<ReviewRes> reviewResList = new ArrayList<>();

        for (Review review : reviews) {
            ReviewRes reviewRes = ReviewRes.fromEntity(review);
            long betweenDays = ChronoUnit.DAYS.between(review.getSpec().getStartDate(), review.getDate());
            reviewRes.setDPlusDay(betweenDays);

            reviewResList.add(reviewRes);
        }
        return reviewResList;
    }

    @Override
    public List<Review> isCompleted(List<Review> reviews, Spec spec) {
        for (Review review : reviews) {
            LocalDate currentDate = LocalDate.now();
            boolean isCompleted = false;
            if (review.getSpec().getEndDate().isBefore(currentDate)) {  isCompleted = true; }

            spec.setCompleted(isCompleted);
        }
        return reviews;
    }
}

