package tiesiogdvd.orm_database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tiesiogdvd.orm_database.entities.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Integer>{
        }
