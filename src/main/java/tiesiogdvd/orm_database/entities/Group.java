package tiesiogdvd.orm_database.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "group_item")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer group_id;
    private String name;
    private Integer year;

    @OneToMany(mappedBy = "group")
    private List<Client> clients;

    public Group(String name, Integer year) {
        this.name = name;
        this.year = year;
    }

    public Group() {
    }

    public Integer getId() {
        return group_id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + group_id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
