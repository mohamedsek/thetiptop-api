package fr.thetiptop.app.mapper;

import fr.thetiptop.app.dto.UserDto;
import fr.thetiptop.app.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto mapToDto(UserModel userModel);

    UserModel mapToModel(UserDto userDto);
}
