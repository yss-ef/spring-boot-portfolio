package ma.youssef.exemplepatient.service;

import ma.youssef.exemplepatient.entities.Consultation;
import ma.youssef.exemplepatient.entities.Doctor;
import ma.youssef.exemplepatient.entities.Patient;
import ma.youssef.exemplepatient.entities.RendezVous;

import java.util.List;

public interface HospitalService {
    Patient savePatient(Patient patient);
    Doctor saveDoctor(Doctor doctor);
    Consultation saveConsultation(Consultation consultation);
    RendezVous saveRendezVous(RendezVous rendezVous);


    List<Patient> findAllPatient();
    List<Doctor> findAllDoctor();
    List<Consultation> findAllConsultation();
    List<RendezVous> findAllRendezVous();
}
