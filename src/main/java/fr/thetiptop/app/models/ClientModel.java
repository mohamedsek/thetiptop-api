package fr.thetiptop.app.models;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Client")
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<TicketModel> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketModel> tickets) {
        this.tickets = tickets;
    }

    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderId() {
        return providerId;
    }
}
