package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rent_proposal", uniqueConstraints = @UniqueConstraint(columnNames = {"start_date", "end_date", "article_id", "renter_id"}))
public class RentProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_proposal_id_seq")
    @SequenceGenerator(sequenceName = "rent_proposal_id_seq", name = "rent_proposal_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 310)
    private String message;

    @Enumerated(EnumType.ORDINAL)
    private RentState state;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean seen;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "renter_id", referencedColumnName = "id")
    private User renter;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

    @Transient
    private boolean marked;

    /* package */ RentProposal() {
        // Just for Hibernate
    }

    public RentProposal(long id, String message, RentState state, LocalDate startDate, LocalDate endDate) {
        this(message, state, startDate, endDate, false);
        this.id = id;
    }

    public RentProposal(String message, RentState state, LocalDate startDate, LocalDate endDate, boolean seen) {
        this.message = message;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seen = seen;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RentState getState() {
        return state;
    }

    public void setState(RentState state) {
        this.state = state;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public boolean getSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public String toString() {
        return '\n' + "startDate: " + startDate + " | endDate: " +
                endDate + " | Message: " + message + " | Article id: " + article.getId() + '\n';
    }
}
