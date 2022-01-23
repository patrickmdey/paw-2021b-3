package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Review;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;

public class ReviewDTO {

    private int rating;
    private String message;
    private LocalDate createdAt;

    private URI url;
    private URI articleUrl;
    private URI renterUrl;

    // Post only params
    private long articleId;
    private long renterId;

    public static ReviewDTO fromReview(Review review, UriInfo uri){
        ReviewDTO toReturn = new ReviewDTO();
        toReturn.rating = review.getRating();
        toReturn.message = review.getMessage();
        toReturn.createdAt = review.getCreatedAt();
        toReturn.url = uri.getBaseUriBuilder().path("reviews").path(String.valueOf(review.getId())).build();
        toReturn.articleUrl = uri.getBaseUriBuilder().path("articles").path(String.valueOf(review.getArticle().getId())).build();
        return toReturn;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public URI getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(URI articleUrl) {
        this.articleUrl = articleUrl;
    }

    public URI getRenterUrl() {
        return renterUrl;
    }

    public void setRenterUrl(URI renterUrl) {
        this.renterUrl = renterUrl;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getRenterId() {
        return renterId;
    }

    public void setRenterId(long renterId) {
        this.renterId = renterId;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }
}
