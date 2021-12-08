package com.amr.project.converter;

import com.amr.project.model.dto.CityDto;
import com.amr.project.model.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring"/*, uses = AddressMapper.class*/)
public interface CityMapper {

    CityDto cityToCityDto(City city);

    City cityDtoToCity(CityDto cityDto);

}
