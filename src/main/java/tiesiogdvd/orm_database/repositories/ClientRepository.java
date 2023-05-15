package tiesiogdvd.orm_database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tiesiogdvd.orm_database.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findClientByUsername(String username);
}
