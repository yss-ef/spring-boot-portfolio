package ma.youssef.exemplepatient.repositories;

import ma.youssef.exemplepatient.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
