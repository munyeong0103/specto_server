package spectacle.specto.service;

import spectacle.specto.domain.Review;
import spectacle.specto.dto.reviewDto.req.ReviewDto;

import java.time.LocalDate;
import java.util.List;


public interface ReviewService {
   void postReview(ReviewDto reviewDto, String user_id);

}
