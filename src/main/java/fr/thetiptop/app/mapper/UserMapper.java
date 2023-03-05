package fr.thetiptop.app.mapper;

import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.models.ClientModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto clientToUserDto(ClientModel clientModel);

    ClientModel userDtoToClient(UserDto userDto);
}
