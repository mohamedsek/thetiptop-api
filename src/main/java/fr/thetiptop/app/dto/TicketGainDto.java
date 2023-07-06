package fr.thetiptop.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketGainDto {


    private Long ticketId;
    private String ticketCode;
    private String gainTitle;
    private Boolean isUsed;


}
