package com.amr.project.converter;

import com.amr.project.model.dto.CountryDto;
import com.amr.project.model.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDto countryToCountryDto(Country country);

    Country countryDtoToCountry(CountryDto countryDto);
}
