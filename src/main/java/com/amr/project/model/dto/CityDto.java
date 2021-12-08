package com.amr.project.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class CityDto {
    private Long id;
    private String name;
//    private List<AddressDto> addresses;
}
