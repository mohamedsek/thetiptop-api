package fr.thetiptop.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Client")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel extends UserModel {

    @Column(nullable = false)
    protected String fullname;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type")
    private AuthenticationProvider authenticationProvider;

    @OneToMany(mappedBy = "client")
    private List<TicketModel> tickets;

    @Column(nullable = true)
    private String providerId;
}
