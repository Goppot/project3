package com.amr.project.webapp.paypalsettings;

import com.amr.project.model.entity.Setting;

import java.util.List;

/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 * Список настроек API PayPal
 */


public class PaymentSettingBag extends Settings {

    public PaymentSettingBag(List<Setting> listSettings) {
        super(listSettings);
    }

    public String getURL() {
        return getValue("PAYPAL_API_BASE_URL");
    }

    public String getClientID() {
        return getValue("PAYPAL_API_CLIENT_ID");
    }

    public String getClientSecret() {
        return getValue("PAYPAL_API_CLIENT_SECRET");
    }

}
