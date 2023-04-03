package tiesiogdvd.orm_database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tiesiogdvd.orm_database.entities.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
}
