package com.amr.project.webapp.paypalsettings;

/**
 * User: Hajimurad Suleymanov
 * Date: 09.11.2021
 */

import com.amr.project.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayPalOrderResponse {

    private String id;
    private Status status;

    public boolean validate(String orderId) {
        return id.equals(orderId) && status.equals(Status.PAID);
    }

}
