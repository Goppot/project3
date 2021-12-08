package com.amr.project.webapp.paypalsettings;

/**
 * User: Hajimurad Suleymanov
 * Date: 09.11.2021
 */

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
public class CheckOutInfo {

    private float itemCost;
    private BigDecimal total;
    private float shippingCost;
    private float paymentTotal;
    private int deliverDays;
    private boolean codSupported;

    public boolean isCashOnDeliverySupported() {
        return codSupported;
    }

    public String getPaymentTotal() {
        DecimalFormat formatter = new DecimalFormat("##.##");
        return formatter.format(paymentTotal);
    }
    public Date getDeliverDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, deliverDays);

        return calendar.getTime();
    }

}
