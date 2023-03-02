package fr.thetiptop.app.dto;

public class TicketDto {

    private Long id;

    private String code;

    private Boolean isParticipating;

    private Boolean isUsed;

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
}
