package jp.co.scop.sensors.domain.validator;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.common.base.Strings;

import jp.co.scop.sensors.domain.SensorStatusRequest;
import jp.co.scop.sensors.domain.SensorStatusSensorRequest;
import jp.co.scop.sensors.domain.UrineAmountSensorRequest;


/**
 * 企業情報関連のリクエストに対するバリデータ
 */
@Component
public class SensorStatusValidator implements Validator {

	// 共通バリデーション
	@Autowired
	CommonValidator commonValidator;
	
	/**
	 * バリデート対象クラスを指定
	 */
    @Override
    public boolean supports(Class<?> clazz) {
        return SensorStatusRequest.class.isAssignableFrom(clazz);
    }

    /**
     * バリデーション
     */
    @Override
    public void validate(Object request, Errors errors) {
    	
    	SensorStatusRequest sensorStatusRequest = (SensorStatusRequest)request;
    	
    	// センサータイプバリデーション
    	if ( Strings.isNullOrEmpty(sensorStatusRequest.getSensorType())) {
    		// 必須チェック
    		errors.rejectValue("sensorType", "validation.sensorType.require");
    	} else {
    		// 対象センサータイプチェック
        	if ( commonValidator.sensorTypeValidate(sensorStatusRequest.getSensorType()) ) {
        		errors.rejectValue("sensorType", "validation.sensorType.target");
        	}
    	}
    	
    	// sensorsバリデーション
    	if ( sensorStatusRequest.getSensors().size() == 0) {
    		// 必須チェック
    		errors.rejectValue("sensors", "validation.sensors.require");
    	} else {
    		// センサーステータスコードチェック
    		for (SensorStatusSensorRequest data: sensorStatusRequest.getSensors()) {
    			if( Objects.isNull(data.getSensorStatusCode()) ) {
    				errors.rejectValue("", "validation.common.target");
    			}
    		}
    	}
    	
    }

}
