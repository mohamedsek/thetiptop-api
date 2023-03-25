package fr.thetiptop.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Gain")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GainModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double chance;

    @Column
    private String imageUrl;

    @OneToMany(mappedBy = "gain")
    private List<TicketModel> tickets;
}
