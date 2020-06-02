package jp.co.scop.sensors.domain.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.common.base.Strings;

import jp.co.scop.sensors.domain.SettingsRequest;
import jp.co.scop.sensors.service.SensorGroupsService;


/**
 * センサー機器登録のリクエストに対するバリデータ
 */
@Component
public class SettingsValidator implements Validator {

	// 共通バリデーション
	@Autowired
	CommonValidator commonValidator;
	
	// センサー管理Service
	@Autowired
	SensorGroupsService sensorGroupsService;
	
	/**
	 * バリデート対象クラスを指定
	 */
    @Override
    public boolean supports(Class<?> clazz) {
        return SettingsRequest.class.isAssignableFrom(clazz);
    }

    /**
     * バリデーション
     */
    @Override
    public void validate(Object request, Errors errors) {
    	
    	SettingsRequest settingsRequest = (SettingsRequest)request;
    	
    	// 管理PCバリデーション
    	if (Strings.isNullOrEmpty(settingsRequest.getClientId()) ) {
    		// 必須チェック
    		errors.rejectValue("sensorType", "validation.sensorType.require");
    	}
    	
    	// センサータイプバリデーション
    	if ( Strings.isNullOrEmpty(settingsRequest.getSensorType()) ) {
    		// 必須チェック
    		errors.rejectValue("sensorType", "validation.sensorType.require");
    	} else {
    		// 対象センサータイプチェック
    		if ( commonValidator.sensorTypeValidate(settingsRequest.getSensorType()) ) {
    			errors.rejectValue("sensorType", "validation.sensorType.target");
    		}
    	}
    	
    	// sensorsバリデーション
    	if ( settingsRequest.getSensors().size() == 0) {
    		// 必須チェック
    		errors.rejectValue("sensors", "validation.sensors.require");
    	}
    }
}
