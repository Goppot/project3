package com.amr.project.converter;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = AddressMapper.class, componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "address.country.name", target = "country")
    @Mapping(source = "address.city.name", target = "city")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.house", target = "house")

    @Mapping(source = "country", target = "address.country.name")
    @Mapping(source = "city", target = "address.city.name")
    @Mapping(source = "street", target = "address.street")
    @Mapping(source = "house", target = "address.house")

    List<OrderDto> listOrderToDto(List<Order> orders);

    List<Order> listDtoToOrder(List<OrderDto> orderDtos);

    OrderDto orderToDto(Order order);
}