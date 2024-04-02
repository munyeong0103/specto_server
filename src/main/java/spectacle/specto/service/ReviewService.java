package spectacle.specto.service;

import spectacle.specto.domain.Review;
import spectacle.specto.dto.reviewDto.req.PostReviewReq;
import spectacle.specto.dto.reviewDto.req.UpdateReviewReq;
import spectacle.specto.dto.reviewDto.res.ReviewDetail;
import spectacle.specto.dto.reviewDto.res.ReviewProgress;
import spectacle.specto.dto.reviewDto.res.ReviewRes;

import java.time.LocalDate;
import java.util.List;


public interface ReviewService {
   void postReview(PostReviewReq postReviewReq, String user_id);

   List<ReviewRes> getReviewByCalendarAndDate(LocalDate date);

   List<ReviewProgress> getReviewByCalendarAndProgress(int year, int month);

   List<ReviewRes> getReviewBySpecSortedByRecent(long specId);

   List<ReviewRes> getReviewBySpecSortedByOldest(long specId);

   List<ReviewRes> getReviewBySpecSortedByMostViews(long specId);

   ReviewDetail getReviewDetail(long reviewId);

   boolean updateReview(UpdateReviewReq updateReviewReq, long reviewId);

   List<ReviewRes> addDPlusDay(List<Review> reviews);
}
