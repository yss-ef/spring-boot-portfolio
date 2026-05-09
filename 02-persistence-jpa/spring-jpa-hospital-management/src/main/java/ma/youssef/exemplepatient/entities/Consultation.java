package ma.youssef.exemplepatient.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Consultation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String report;

    @OneToOne(fetch = FetchType.LAZY)
    private RendezVous rendezVous;
}
