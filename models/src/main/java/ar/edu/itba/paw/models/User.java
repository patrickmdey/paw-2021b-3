package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @SequenceGenerator(sequenceName = "account_id_seq", name = "account_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @Column(length = 320, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private Locations location;

    @Enumerated(EnumType.ORDINAL)
    private UserType type;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "picture", referencedColumnName = "id")
    private DBImage picture;

    /* package */ User() {
        // Just for Hibernate
    }

    public User(String email, String password, String firstName, String lastName, Locations location, DBImage picture, UserType type) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.picture = picture;
        this.type = type;
    }

    public User(long id, String email, String password, String firstName, String lastName, Locations location, DBImage picture, UserType type) {
        this(email, password, firstName, lastName, location, picture, type);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public DBImage getPicture() {
        return picture;
    }

    public void setPicture(DBImage picture) {
        this.picture = picture;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + " | Id: " + id;
    }
}
