package spectacle.specto.service;

import spectacle.specto.domain.Review;
import spectacle.specto.dto.reviewDto.req.ReviewDto;
import spectacle.specto.dto.reviewDto.res.ReviewProgress;
import spectacle.specto.dto.reviewDto.res.ReviewRes;

import java.time.LocalDate;
import java.util.List;


public interface ReviewService {
   void postReview(ReviewDto reviewDto, String user_id);

   List<ReviewRes> getReviewByCalendarAndDate(LocalDate date);

   List<ReviewProgress> getReviewByCalendarAndProgress(int year, int month);

   List<ReviewRes> getReviewBySpecSortedByRecent(long specId);

   List<ReviewRes> getReviewBySpecSortedByOldest(long specId);

   List<ReviewRes> getReviewBySpecSortedByMostViews(long specId);

   List<ReviewRes> addDPlusDay(List<Review> reviews);
}
