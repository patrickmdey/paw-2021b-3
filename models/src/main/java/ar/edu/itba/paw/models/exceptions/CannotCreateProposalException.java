package ar.edu.itba.paw.models.exceptions;

public class CannotCreateProposalException extends InternalErrorException {
    @Override
    public String getMessage() {
        return "exception.CannotCreateProposal";
    }
}
