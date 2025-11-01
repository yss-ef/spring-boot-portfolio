package ma.youssef.exemplepatient.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date birthDate;
    private boolean isSick;

    @OneToMany(mappedBy = "patient", fetch =  FetchType.LAZY)
    private Collection<RendezVous> rendezVous;

}
