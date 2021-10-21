package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Primary
public class ReviewDaoJpa implements ReviewDao {

    private final static long RESULTS_PER_PAGE = 3L;

    @PersistenceContext
    private EntityManager em;

    private StringBuilder queryBuilder(String fields) {
        return new StringBuilder("SELECT " + fields + " FROM review WHERE article_id = :article_id ");
    }

    @Override
    public float getAverage(long articleId) {
        Query query = em.createNativeQuery(queryBuilder("COALESCE(AVG(rating), 0)").toString());
        query.setParameter("article_id", articleId);
        return Float.parseFloat(query.getSingleResult().toString());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Review> getPaged(long articleId, long page) {
        StringBuilder queryBuilder = queryBuilder("id");
        queryBuilder.append("ORDER BY created_at DESC LIMIT :limit OFFSET :offset");

        Query query = em.createNativeQuery(queryBuilder.toString());
        query.setParameter("article_id", articleId);
        query.setParameter("limit", RESULTS_PER_PAGE);
        query.setParameter("offset", (page - 1) * RESULTS_PER_PAGE);

        List<Integer> aux = query.getResultList();

        List<Long> reviewIds = aux.stream().mapToLong(Integer::longValue).boxed().collect(Collectors.toList());

        if (reviewIds.isEmpty())
            return new ArrayList<>();

        TypedQuery<Review> reviewQuery = em.createQuery("from Review " +
                "WHERE id IN (:reviewIds) ORDER BY createdAt", Review.class);

        reviewQuery.setParameter("reviewIds", reviewIds);

        return reviewQuery.getResultList();
    }

    @Override
    public Long getMaxPage(long articleId) {

        Query query = em.createNativeQuery(queryBuilder("COUNT(*)").toString());
        query.setParameter("article_id", articleId);
        long size = Long.parseLong(query.getSingleResult().toString());
        int toSum = (size % RESULTS_PER_PAGE == 0) ? 0 : 1;

        return (size / RESULTS_PER_PAGE) + toSum;
    }

    @Override
    public boolean hasReviewed(long userId, long articleId) {
        final TypedQuery<Review> query = em.createQuery(
                "from Review as r WHERE r.renter.id = :renter AND r.article.id = :article_id", Review.class);

        query.setParameter("renter", userId);
        query.setParameter("article_id", articleId);

        return !query.getResultList().isEmpty();
    }

    @Override
    public Review create(int rating, String message, long articleId, long renterId) {
        Article article = em.find(Article.class, articleId);//articleService.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        User renter = em.find(User.class, renterId);//userService.findById(renterId).orElseThrow(UserNotFoundException::new);

        Review review = new Review(rating, message, new Date(System.currentTimeMillis()));
        review.setArticle(article);
        review.setRenter(renter);

        em.persist(review);
        return review;
    }

    @Override
    public Optional<Review> findById(long reviewId) {
        return Optional.ofNullable(em.find(Review.class, reviewId));
    }

}
