package spectacle.specto.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spectacle.specto.domain.Review;
import spectacle.specto.domain.Spec;
import spectacle.specto.domain.User;
import spectacle.specto.dto.reviewDto.req.ReviewDto;
import spectacle.specto.dto.reviewDto.res.ReviewRes;
import spectacle.specto.repository.ReviewRepository;
import spectacle.specto.repository.SpecRepository;
import spectacle.specto.repository.UserRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
}
