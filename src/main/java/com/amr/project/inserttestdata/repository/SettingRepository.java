package com.amr.project.inserttestdata.repository;

import com.amr.project.model.entity.Setting;
import com.amr.project.model.enums.PaymentCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 */

public interface SettingRepository extends CrudRepository<Setting, String> {

    public List<Setting> findByCategory(PaymentCategory category);
    public Setting findByKey(String key);

}
