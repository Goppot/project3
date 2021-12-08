package com.amr.project.webapp.paypalsettings;

import com.amr.project.model.entity.Setting;

import java.util.List;

/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 * Список настроек валюты для API PayPal
 */

public class CurrencySettingBag extends Settings {

    public CurrencySettingBag(List<Setting> listSettings) {
        super(listSettings);
    }

    public String getSymbol() {
        return getValue("CURRENCY_SYMBOL");
    }

    public String getSymbolPosition() {
        return super.getValue("CURRENCY_SYMBOL_POSITION");
    }

    public String getDecimalPointType() {
        return super.getValue("DECIMAL_POINT_TYPE");
    }

    public String getThousandPointType() {
        return super.getValue("THOUSANDS_POINT_TYPE");
    }

    public int getDecimalDigits() {
        return Integer.parseInt(super.getValue("DECIMAL_DIGITS"));
    }
}
