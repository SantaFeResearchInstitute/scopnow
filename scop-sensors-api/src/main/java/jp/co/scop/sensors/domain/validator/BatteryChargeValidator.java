package jp.co.scop.sensors.domain.validator;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.common.base.Strings;

import jp.co.scop.sensors.domain.BatteryChargerRequest;
import jp.co.scop.sensors.domain.BatteryChargerSensorRequest;
import jp.co.scop.sensors.domain.SensorStatusRequest;
import jp.co.scop.sensors.domain.UrineAmountSensorRequest;


/**
 * バッテリー状態更新のリクエストに対するバリデータ
 */
@Component
public class BatteryChargeValidator implements Validator {

	@Autowired
	CommonValidator commonValidator;
	
	/**
	 * バリデート対象クラスを指定
	 */
    @Override
    public boolean supports(Class<?> clazz) {
        return BatteryChargerRequest.class.isAssignableFrom(clazz);
    }

    /**
     * バリデーション
     */
    @Override
    public void validate(Object request, Errors errors) {
    	BatteryChargerRequest batteryChargerRequest = (BatteryChargerRequest)request;
    	
    	// センサータイプバリデーション
    	if ( Strings.isNullOrEmpty(batteryChargerRequest.getSensorType())) {
    		// 必須チェック
    		errors.rejectValue("sensorType", "validation.sensorType.require");
    	} else {
    		// 対象センサータイプチェック
        	if ( commonValidator.sensorTypeValidate(batteryChargerRequest.getSensorType()) ) {
        		errors.rejectValue("sensorType", "validation.sensorType.target");
        	}
    	}
    	
    	// sensorsバリデーション
    	if ( batteryChargerRequest.getSensors().size() == 0) {
    		// 必須チェック
    		errors.rejectValue("sensors", "validation.sensors.require");
    	} else {
    		// 電池残量チェック
    		for (BatteryChargerSensorRequest data: batteryChargerRequest.getSensors()) {
    			if( Objects.isNull(data.getCharge()) ) {
    				errors.rejectValue("", "validation.common.target");
    			}
    		}
    	}
    	
    }

}
