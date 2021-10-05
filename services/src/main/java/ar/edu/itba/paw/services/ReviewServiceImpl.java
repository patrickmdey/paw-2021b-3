package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<Review> getPaged(long articleId, long page) {
        List<Review> reviews = reviewDao.getPaged(articleId, page);
        reviews.forEach(review -> {
            review.setRenter(userService.
                    findById(review.getRenterId()).
                    orElseThrow(RuntimeException::new));
        });
        return reviews;
    }

    @Override
    public int articleRating(long articleId) {
        return Math.round(reviewDao.getAverage(articleId));
    }

    @Override
    @Transactional
    public Optional<Review> create(int rating, String message, long articleId, long renterId) {
        Optional<Article> article = articleService.findById(articleId);
        if (!article.isPresent())
            throw new ArticleNotFoundException();

        Optional<Review> review = reviewDao.create(rating, message, articleId, renterId);
        review.ifPresent(value -> value.setRenter(userService.findById(renterId).orElseThrow(UserNotFoundException::new)));
        return review;
    }

    @Override
    public Long getMaxPage(long articleId) {
        return reviewDao.getMaxPage(articleId);
    }

    @Override
    public boolean hasReviewed(User user, Long articleId) {
        if (user == null || articleId == null)
            return false;
        return reviewDao.hasReviewed(user.getId(), articleId);
    }

    @Override
    public Optional<Review> findById(long reviewId) {
        return reviewDao.findById(reviewId);
    }

    @Override
    public void update(int rating, String message, long reviewId) {
        reviewDao.update(rating, message, reviewId);
    }

}
