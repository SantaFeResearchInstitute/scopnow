package jp.co.scop.sensors.domain.validator;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.common.base.Strings;

import jp.co.scop.sensors.domain.UrineAmountRequest;
import jp.co.scop.sensors.domain.UrineAmountSensorRequest;


/**
 * 企業情報関連のリクエストに対するバリデータ
 */
@Component
public class UrineAmountValidator implements Validator {

	// 共通バリデーション
	@Autowired
	CommonValidator commonValidator;

	/**
	 * バリデート対象クラスを指定
	 */
    @Override
    public boolean supports(Class<?> clazz) {
        return UrineAmountRequest.class.isAssignableFrom(clazz);
    }

    /**
     * バリデーション
     */
    @Override
    public void validate(Object request, Errors errors) {
    	
    	UrineAmountRequest urineAmountRequest = (UrineAmountRequest)request;
    	
    	// センサータイプバリデーション
    	if ( Strings.isNullOrEmpty(urineAmountRequest.getSensorType())) {
    		// 必須チェック
    		errors.rejectValue("", "validation.sensorType.require");
    	} else {
    		// 対象センサータイプチェック
        	if ( commonValidator.sensorTypeValidate(urineAmountRequest.getSensorType()) ) {
        		errors.rejectValue("", "validation.sensorType.target");
        	}
    	}
    	
    	// sensorsバリデーション
    	if (Objects.isNull(urineAmountRequest.getSensors())) {
    		// 必須チェック
    		errors.rejectValue("", "validation.sensors.require");
    	}
    	
    }

}
