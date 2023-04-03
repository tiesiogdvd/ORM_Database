package tiesiogdvd.orm_database.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "workout")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workout_id;
    private String name;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "date")
    private Date date;
    private int places;
    private String location;

    @OneToMany(mappedBy = "workout")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Registration> registrations;

    public Workout() {

    }


    public Integer getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(Integer workout_id) {
        this.workout_id = workout_id;
    }

    public Workout(String name, Date date, int places, String location) {
        this.name = name;
        this.date = date;
        this.places = places;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + workout_id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", places=" + places +
                ", location='" + location + '\'' +
                ", registrations=" + registrations +
                '}';
    }
}
