package fr.thetiptop.app.mapper;

import fr.thetiptop.app.dto.GainDto;
import fr.thetiptop.app.models.GainModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GainMapper {

    GainMapper INSTANCE = Mappers.getMapper(GainMapper.class);

    GainDto gainToGainDto(GainModel gainModel);

    GainModel gainToGainModel(GainDto gainDto);
}
