package fr.thetiptop.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Ticket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Boolean isParticipating;

    @Column(nullable = false)
    private Boolean isUsed;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private ClientModel client;

    @ManyToOne
    @JoinColumn(name = "gain_id", nullable = true)
    private GainModel gain;
}
