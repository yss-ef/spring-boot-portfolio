package ma.youssef.exemplepatient.service;

import jakarta.transaction.Transactional;
import ma.youssef.exemplepatient.entities.Consultation;
import ma.youssef.exemplepatient.entities.Doctor;
import ma.youssef.exemplepatient.entities.Patient;
import ma.youssef.exemplepatient.entities.RendezVous;
import ma.youssef.exemplepatient.repositories.ConsultationRepository;
import ma.youssef.exemplepatient.repositories.DoctorRepository;
import ma.youssef.exemplepatient.repositories.PatientRepository;
import ma.youssef.exemplepatient.repositories.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class HospitalServiceImpl implements HospitalService {
    private PatientRepository patientRepository;
    private ConsultationRepository consultationRepository;
    private RendezVousRepository rendezVousRepository;
    private DoctorRepository doctorRepository;

    // Méthode alternative a @Autowired et plus conseille.
    public HospitalServiceImpl(PatientRepository patientRepository, ConsultationRepository consultationRepository, RendezVousRepository rendezVousRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.consultationRepository = consultationRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Override
    public RendezVous saveRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public List<Patient> findAllPatient() {
        return patientRepository.findAll();
    }

    @Override
    public List<Doctor> findAllDoctor() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Consultation> findAllConsultation() {
        return consultationRepository.findAll();
    }

    @Override
    public List<RendezVous> findAllRendezVous() {
        return rendezVousRepository.findAll();
    }

}
