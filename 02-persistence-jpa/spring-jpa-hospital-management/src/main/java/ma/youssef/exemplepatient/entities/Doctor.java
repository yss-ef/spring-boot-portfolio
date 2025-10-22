package ma.youssef.exemplepatient.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String specialty;
    private boolean status;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;
}
