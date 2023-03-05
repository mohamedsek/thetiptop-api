package fr.thetiptop.app.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Client")
@Data
@NoArgsConstructor
public class ClientModel extends UserModel {
    @Builder
    public ClientModel(Long id, String email, String uid, String firstName, String lastName, String password, UserRoleModel role, boolean enabled, AuthenticationProvider authenticationProvider, List<TicketModel> tickets, String providerId) {
        super(id, email, uid, firstName, lastName, password, role, enabled);
        this.authenticationProvider = authenticationProvider;
        this.tickets = tickets;
        this.providerId = providerId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_type")
    private AuthenticationProvider authenticationProvider;

    @OneToMany(mappedBy = "client")
    private List<TicketModel> tickets;

    @Column(nullable = true)
    private String providerId;
}
