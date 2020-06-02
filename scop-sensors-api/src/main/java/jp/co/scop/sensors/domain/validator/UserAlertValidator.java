package jp.co.scop.sensors.domain.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.common.base.Strings;

import jp.co.scop.sensors.domain.UserAlertRequest;



/**
 * 企業情報関連のリクエストに対するバリデータ
 */
@Component
public class UserAlertValidator implements Validator {

	// 共通バリデーション
	@Autowired
	CommonValidator commonValidator;
	
	/**
	 * バリデート対象クラスを指定
	 */
    @Override
    public boolean supports(Class<?> clazz) {
        return UserAlertRequest.class.isAssignableFrom(clazz);
    }

    /**
     * バリデーション
     */
    @Override
    public void validate(Object request, Errors errors) {
    	
    	UserAlertRequest userAlertRequest = (UserAlertRequest)request;
    	
    	// センサータイプバリデーション
    	if ( Strings.isNullOrEmpty(userAlertRequest.getSensorType())) {
    		// 必須チェック
    		errors.rejectValue("sensorType", "validation.sensorType.require");
    	} else {
    		// 対象センサータイプチェック
    		if( commonValidator.sensorTypeValidate(userAlertRequest.getSensorType()) ) {
        		errors.rejectValue("sensorType", "validation.sensorType.target");
    		}
    	}
    	
    	// sensorsバリデーション
    	if ( userAlertRequest.getSensors().size() == 0) {
    		// 必須チェック
    		errors.rejectValue("sensors", "validation.sensors.require");
    	}
    	
    }
    

}
