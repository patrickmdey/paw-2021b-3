package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.RentProposal;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RentDao {

    List<RentProposal> list(long ownerId);

    Optional<RentProposal> findById(long id);

    void acceptRequest(long requestId);

    void deleteRequest(long requestId);

    Optional<RentProposal> create(String comment, Boolean approved, Date startDate, Date endDate, Integer articleId, long renterId);
}