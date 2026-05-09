package ma.youssef.exemplepatient;

import ma.youssef.exemplepatient.entities.Consultation;
import ma.youssef.exemplepatient.entities.Doctor;
import ma.youssef.exemplepatient.entities.Patient;
import ma.youssef.exemplepatient.entities.RendezVous;
import ma.youssef.exemplepatient.repositories.ConsultationRepository;
import ma.youssef.exemplepatient.repositories.DoctorRepository;
import ma.youssef.exemplepatient.repositories.PatientRepository;
import ma.youssef.exemplepatient.repositories.RendezVousRepository;
import ma.youssef.exemplepatient.service.HospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ExemplePatientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExemplePatientApplication.class, args);

    }

    @Bean
        return args -> {
            List.of("Pat1", "Pat2", "Pat3", "Pat4", "Pat5", "Pat6", "Pat7").forEach(pat -> {
                Patient patient = new Patient();
                patient.setName(pat);
                patient.setSick(true);
                patient.setBirthDate(new Date());
                System.out.println(patient.toString());
            });

            List.of("Doc1", "Doc2", "Doc3", "Doc4", "Doc5", "Doc6", "Doc7").forEach(doc -> {
                Doctor doctor = new Doctor();
                doctor.setName(doc);
                doctor.setEmail(doc+"@gmail.com");
                doctor.setSpecialty("Doctor");
                doctor.setStatus(true);
            });


            patients.forEach(patient -> {
                doctors.forEach(doctor -> {
                    RendezVous rendezVous = new RendezVous();
                    rendezVous.setDateRdv(new Date());
                    rendezVous.setPatient(patient);
                    rendezVous.setDoctor(doctor);
                    rendezVous.setDone(false);
                });
            });


            rendezVouses.forEach(rendezVous -> {
                Consultation consultation = new Consultation();
                consultation.setDate(rendezVous.getDateRdv());
                consultation.setReport("Report consultation" + consultation.getId());
                consultation.setRendezVous(rendezVous);
            });

            List<Consultation> consultations = hospitalService.findAllConsultation();
        };
    }
}
