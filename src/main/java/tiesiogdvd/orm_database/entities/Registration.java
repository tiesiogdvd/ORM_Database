package tiesiogdvd.orm_database.entities;


import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "registration_date")
    private Date registrationDate;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "client_id") //joins with parent id column
    private Client client;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "workout_id") //joins with parent id column
    private Workout workout;

    public Registration() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Registration(Date registrationDate, Client client, Workout workout) {
        this.registrationDate = registrationDate;
        this.client = client;
        this.workout = workout;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}
