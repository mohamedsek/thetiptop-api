package fr.thetiptop.app.models;

import jakarta.persistence.*;

@Entity(name = "Ticket")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getParticipating() {
        return isParticipating;
    }

    public void setParticipating(Boolean participating) {
        isParticipating = participating;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public GainModel getGain() {
        return gain;
    }

    public void setGain(GainModel gain) {
        this.gain = gain;
    }
}
