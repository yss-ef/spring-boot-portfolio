package ma.youssef.exemplepatient.repositories;

import ma.youssef.exemplepatient.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    public List<Patient> findByName(String nom);

    @Query("select p from Patient p where p.id = :x")
    public List<Patient> findId(@Param("x") Long id);
}
