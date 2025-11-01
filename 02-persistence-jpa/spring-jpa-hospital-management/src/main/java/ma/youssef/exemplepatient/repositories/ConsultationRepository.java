package ma.youssef.exemplepatient.repositories;

import ma.youssef.exemplepatient.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

}
