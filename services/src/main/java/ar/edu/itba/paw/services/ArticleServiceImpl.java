package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.ArticleDao;
import ar.edu.itba.paw.interfaces.service.ArticleService;
import ar.edu.itba.paw.interfaces.service.CategoryService;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.DBImage;
import ar.edu.itba.paw.models.Locations;
import ar.edu.itba.paw.models.OrderOptions;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;
    
    @Override
    @Transactional(readOnly = true)
    public List<Article> get(String name, Long category, OrderOptions orderBy, Long user, Locations location, Float initPrice, Float endPrice, Long renterId, Long limit, long page) {
        validateRequest(category, orderBy, user, location, initPrice, endPrice, renterId);
        if (renterId != null)
            return rentedArticles(renterId, page, limit);

        OrderOptions orderOp = OrderOptions.LOWER_ARTICLE;
        if (orderBy != null)
            orderOp = orderBy;

        return this.articleDao.filter(name, category, orderOp, user, location == null ? null : (long) location.ordinal(), initPrice, endPrice, limit, page);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> findById(long articleId) {
        return articleDao.findById(articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getMaxPage(String name, Long category, Long userId, Locations location, Float initPrice, Float endPrice, Long renterId, Long limit) {
        validateRequest(category, null, userId, location, initPrice, endPrice, renterId);

        if (renterId != null)
            return getRentedMaxPage(renterId, limit);

        return articleDao.getMaxPage(name, category, userId, location == null ? null : (long) location.ordinal(), initPrice, endPrice, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> recommendedArticles(long articleId) {
        return articleDao.recommendedArticles(articleId);
    }

    @Override
    public List<Locations> getUsedLocations() {
        return articleDao.getUsedLocations().stream()
                .sorted(Comparator.comparing(Locations::getName))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Article createArticle(String title, String description, float pricePerDay, List<Long> categories, List<byte[]> images, long idOwner) {
        Article article = articleDao.createArticle(title, description, pricePerDay, idOwner);

        if (categories != null) {
            article.setCategories(categories.stream().map(c -> categoryService.findById(c)
                    .orElseThrow(CategoryNotFoundException::new)).collect(Collectors.toSet()));
        }
        images.forEach(image -> {
            DBImage img = imageService.create(image);
            article.addImage(img);
        });
        return article;
    }

    @Override
    @Transactional
    public Optional<Article> editArticle(long id, String title, String description, float pricePerDay, List<Long> categories) {
        Article article = articleDao.findById(id).orElseThrow(ArticleNotFoundException::new);
        article.setTitle(title);
        article.setDescription(description);
        article.setPricePerDay(pricePerDay);

        article.setCategories(categories.stream().map(c -> categoryService.findById(c)
                .orElseThrow(CategoryNotFoundException::new)).collect(Collectors.toSet()));
        
        return Optional.of(article);
    }

    private void validateRequest(Long category, OrderOptions orderBy, Long userId, Locations location, Float initPrice, Float endPrice, Long renterId) {
        if ((category != null || orderBy != null || userId != null ||
                location != null || initPrice != null || endPrice != null) && renterId != null)
                    throw new IllegalArgumentException("IllegalArguments.articles.list");
    }

    private List<Article> rentedArticles(long renterId, long page, Long limit) {
        return articleDao.rentedArticles(renterId, page, limit);
    }

    private long getRentedMaxPage(long renterId, Long limit) {
        return articleDao.getRentedMaxPage(renterId, limit);
    }
}
