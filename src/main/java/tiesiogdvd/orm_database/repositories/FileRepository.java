package tiesiogdvd.orm_database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tiesiogdvd.orm_database.entities.ClientFile;

public interface FileRepository extends JpaRepository<ClientFile, Integer> {
}
