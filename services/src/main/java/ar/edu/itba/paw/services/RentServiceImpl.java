package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.RequestsGetter;
import ar.edu.itba.paw.interfaces.dao.RentDao;
import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.interfaces.service.RentService;
import ar.edu.itba.paw.models.RentProposal;
import ar.edu.itba.paw.models.RentState;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.RentProposalNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Service
public class RentServiceImpl implements RentService {

    @Autowired
    private RentDao rentDao;

    @Autowired
    private EmailService emailService;

    private List<RentProposal> getRequests(RequestsGetter getter, long accountId, int state, long page) {
        return getter.get(accountId, state, page);
    }

    @Override
    @Transactional
    public List<RentProposal> ownerRequests(long ownerId, RentState state, long page) {
        return getRequests(rentDao::ownerRequests, ownerId, state.ordinal(), page);
    }

    @Override
    @Transactional
    public List<RentProposal> sentRequests(long ownerId, RentState state, long page) {
        List<RentProposal> toReturn = getRequests(rentDao::sentRequests, ownerId, state.ordinal(), page);
        if (!state.equals(RentState.PENDING)){
            toReturn.forEach(proposal -> {
                if(!proposal.getSeen()) {
                    proposal.setSeen(true);
                    proposal.setMarked(true);
                } else {
                    proposal.setMarked(false);
                }
            });
        }
        return toReturn;
    }

    @Override
    @Transactional(readOnly = true)
    public long getReceivedMaxPage(long ownerId, RentState state) {
        return rentDao.getReceivedMaxPage(ownerId, state.ordinal());
    }

    @Override
    @Transactional(readOnly = true)
    public long getSentMaxPage(long ownerId, RentState state) {
        return rentDao.getSentMaxPage(ownerId, state.ordinal());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RentProposal> findById(long id) {
        return rentDao.findById(id);
    }

    @Override
    @Transactional
    public RentProposal create(String message, LocalDate startDate, LocalDate endDate, long articleId,
                               long renterId, String webpageUrl) {
        RentProposal proposal = rentDao.create(message, RentState.PENDING.ordinal(), startDate, endDate, articleId, renterId);
        emailService.sendMailRequest(proposal, proposal.getArticle().getOwner(), webpageUrl);

        return proposal;
    }

    @Override
    @Transactional
    public void setRequestState(long requestId, int state, String webpageUrl) {
        RentState rentState = RentState.values()[state];
        if (rentState.getIsPending())
            return; // TODO: error?

        if (rentState.getIsAccepted())
            acceptRequest(requestId, webpageUrl);
        else
            rejectRequest(requestId, webpageUrl);
    }

    @Override
    @Transactional
    public void acceptRequest(long requestId, String webpageUrl) {
        RentProposal rentProposal = rentDao.findById(requestId).orElseThrow(RentProposalNotFoundException::new);
        rentProposal.setState(RentState.ACCEPTED.ordinal());

        emailService.sendMailRequestConfirmation(rentProposal, rentProposal.getArticle().getOwner(), webpageUrl);
    }

    @Override
    @Transactional
    public void rejectRequest(long requestId, String webpageUrl) {
        RentProposal rentProposal = rentDao.findById(requestId).orElseThrow(RentProposalNotFoundException::new);
        rentProposal.setState(RentState.DECLINED.ordinal());

        emailService.sendMailRequestDenied(rentProposal, rentProposal.getArticle().getOwner(), webpageUrl);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasRented(User renter, long articleId) {
        if (renter == null)
            return false;

        return rentDao.hasRented(renter.getId(), articleId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isPresentSameDate(long renterId, long articleId, LocalDate startDate, LocalDate endDate) {
        return rentDao.isPresentSameDate(renterId, articleId, startDate, endDate);
    }
}
