package com.amr.project.webapp.paypalsettings;

import com.amr.project.model.entity.Setting;

import java.util.List;

/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 * Общие методы для настройки PayPal
 */

public class Settings {

    private List<Setting> listSettings;

    public List<Setting> list() {
        return listSettings;
    }

    public Settings(List<Setting> listSettings) {
        this.listSettings = listSettings;
    }

    public Setting get(String key) {
        int index = listSettings.indexOf(new Setting(key));
        if (index >= 0) {
            return listSettings.get(index);
        }

        return null;
    }

    public String getValue(String key) {
        Setting setting = get(key);
        if (setting != null) {
            return setting.getValue();
        }

        return null;
    }
}
