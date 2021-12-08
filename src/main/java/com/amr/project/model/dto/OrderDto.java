package com.amr.project.model.dto;
import com.amr.project.model.entity.Address;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import lombok.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class OrderDto {
    private Long id;
    private Calendar date;
    private Status status;
    private Address address;
    private BigDecimal total;
    private User user;
    private String buyerName;
    private String buyerPhone;
    private Collection<ItemDto> items;
    private float shippingCost;
    private float itemCost;
    private float subtotal;
    private float tax;
    private Date deliverDate;
}
