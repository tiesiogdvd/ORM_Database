package tiesiogdvd.orm_database.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import java.util.List;


@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer client_id;

    @NotNull
    @Length(min = 3, max = 20, message = "Vardas maziausiai 3 simboliu")
    private String name;
    @NotNull
    @Length(min = 3, max = 25, message = "Pavarde maziausiai 3 simboliu")
    private String surname;

    @NotNull
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "El. pastas netinkamas", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @Length(max = 15)
    private String phone;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "group_id") //joins with parent id column
    private Group group;

    @OneToMany(mappedBy = "client")
    private List<Registration> registrations;

    public Client(String name, String surname, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public Client(String name, String surname, String email, String phone, Group group) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.group = group;
    }

    public Client() {

    }

    public Integer getId() {
        return client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + client_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
