package fr.thetiptop.app.mapper;

import fr.thetiptop.app.dto.TicketDto;
import fr.thetiptop.app.models.TicketModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    TicketDto ticketToTicketDto(TicketModel ticketModel);
    TicketModel ticketToTicketModel(TicketDto ticketDto);
}
