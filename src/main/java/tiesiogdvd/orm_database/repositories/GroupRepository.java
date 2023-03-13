package tiesiogdvd.orm_database.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tiesiogdvd.orm_database.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
