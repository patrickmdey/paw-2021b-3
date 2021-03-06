package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.exceptions.ArticleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Sql("classpath:populateRentTest.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback()
public class RentDaoTest {
    @Autowired
    private RentDao rentDao;
    @PersistenceContext
    private EntityManager em;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    public void ownerRequestsSucceed() {
        // Arrange
        final long idOwner = 1;
        final int state = RentState.ACCEPTED.ordinal();
        final long page = 1;
        final long[] expectedIds = {2, 3};

        // Act
        List<RentProposal> result = rentDao.ownerRequests(idOwner, state, null, page);

        // Assert
        Assert.assertArrayEquals(expectedIds, result.stream().mapToLong(RentProposal::getId).toArray());
    }

    @Test
    public void sentRequestsSucceed() {
        // Arrange
        final long idOwner = 1;
        final int state = RentState.PENDING.ordinal();
        final long page = 1;
        final long[] expectedIds = {1};

        // Act
        List<RentProposal> result = rentDao.ownerRequests(idOwner, state, null, page);

        // Assert
        Assert.assertArrayEquals(expectedIds, result.stream().mapToLong(RentProposal::getId).toArray());
    }

    @Test
    public void findByIdSucceed() {
        // Arrange
        final long idRentProposal = 1;
        final String expectedMessage = "can I rent 1";

        // Act
        Optional<RentProposal> optionalResult = rentDao.findById(idRentProposal);

        // Assert
        Assert.assertTrue(optionalResult.isPresent());
        RentProposal result = optionalResult.get();

        Assert.assertEquals(idRentProposal, result.getId());
        Assert.assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    public void findByIdFailRentProposalNotFound() {
        // Arrange
        final long idRentProposal = 112321;

        // Act
        Optional<RentProposal> optionalResult = rentDao.findById(idRentProposal);

        // Assert
        Assert.assertFalse(optionalResult.isPresent());
    }

    @Test
    public void createSucceed() {
        // Arrange
        final String comment = "";
        final RentState state = RentState.ACCEPTED;
        final LocalDate startDate = LocalDate.parse("2021-11-15", DATE_FORMAT);
        final LocalDate endDate = LocalDate.parse("2021-11-16", DATE_FORMAT);
        final long articleId = 1L;
        final long renterId = 1;

        // Act
        RentProposal r = rentDao.create(comment, state, startDate, endDate, articleId, renterId);

        // Assert
        RentProposal result = em.find(RentProposal.class, r.getId());
        Assert.assertEquals(comment, result.getMessage());
        Assert.assertEquals(state, result.getState());
        Assert.assertEquals(startDate, result.getStartDate());
        Assert.assertEquals(endDate, result.getEndDate());
        Assert.assertEquals(articleId, result.getArticle().getId());
        Assert.assertEquals(renterId, result.getRenter().getId());
    }

    @Test(expected = ArticleNotFoundException.class)
    public void createFailArticleNotFound() {
        // Arrange
        final String comment = "";
        final RentState state = RentState.ACCEPTED;

        final LocalDate startDate = LocalDate.parse("2021-10-05", DATE_FORMAT);
        final LocalDate endDate = LocalDate.parse("2021-11-01", DATE_FORMAT);
        final long articleId = 999;
        final long renterId = 1;

        // Act
        rentDao.create(comment, state, startDate, endDate, articleId, renterId);

        // Assert
        Assert.fail();
    }

    @Test(expected = UserNotFoundException.class)
    public void createFailUserNotFound() {
        // Arrange
        final String comment = "";
        final RentState state = RentState.ACCEPTED;
        final LocalDate startDate = LocalDate.parse("2021-10-05", DATE_FORMAT);
        final LocalDate endDate = LocalDate.parse("2021-11-01", DATE_FORMAT);
        final long articleId = 2;
        final long renterId = 9999;

        // Act
        rentDao.create(comment, state, startDate, endDate, articleId, renterId);

        // Assert
        Assert.fail();
    }

    @Test
    public void hasRentedSucceedReturnTrue() {
        // Arrange
        final long renterId = 2;
        final long articleId = 2;

        // Act
        boolean result = rentDao.hasRented(renterId, articleId);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void hasRentedSucceedReturnFalse() {
        // Arrange
        final long renterId = 2;
        final long articleId = 1;

        // Act
        boolean result = rentDao.hasRented(renterId, articleId);

        // Assert
        Assert.assertFalse(result);
    }

}
