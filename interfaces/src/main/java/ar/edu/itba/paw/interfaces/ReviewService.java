package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getPaged(long articleId, long page);

    int articleRating(long articleId);

    Optional<Review> create(int rating, String message, long articleId, long renterId);

    Long getMaxPage(long articleId);
}
