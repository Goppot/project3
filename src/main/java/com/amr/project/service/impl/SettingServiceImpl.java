package com.amr.project.service.impl;

import com.amr.project.inserttestdata.repository.CurrencyRepository;
import com.amr.project.inserttestdata.repository.SettingRepository;
import com.amr.project.model.entity.Currency;
import com.amr.project.model.entity.Setting;
import com.amr.project.model.enums.PaymentCategory;
import com.amr.project.service.abstracts.SettingService;
import com.amr.project.webapp.paypalsettings.CurrencySettingBag;
import com.amr.project.webapp.paypalsettings.PaymentSettingBag;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 */

@Service
@Transactional
public class SettingServiceImpl implements SettingService {

    private SettingRepository settingRepository;

    private CurrencyRepository currencyRepository;


    @Override
    public CurrencySettingBag getCurrencySettings() {
        List<Setting> settings = settingRepository.findByCategory(PaymentCategory.CURRENCY);
        return new CurrencySettingBag(settings);    }

    @Override
    public String getCurrencyCode() {
        Setting setting = settingRepository.findByKey("CURRENCY_ID");
        Integer currencyId = Integer.parseInt(setting.getValue());
        Currency currency = currencyRepository.findById(currencyId).get();

        return currency.getCode();
    }

    @Override
    public PaymentSettingBag getPaymentSettings() {
        List<Setting> settings = settingRepository.findByCategory(PaymentCategory.PAYMENT);
        return new PaymentSettingBag(settings);    }
}
