package jp.co.scop.sensors.domain.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.common.base.Strings;

import jp.co.scop.sensors.domain.SensorTokenRequest;
import jp.co.scop.sensors.service.SensorGroupsService;


/**
 * 企業情報関連のリクエストに対するバリデータ
 */
@Component
public class SensorTokenIssueValidator implements Validator {

	@Autowired
	SensorGroupsService sensorGroupsService;

	/**
	 * バリデート対象クラスを指定
	 */
    @Override
    public boolean supports(Class<?> clazz) {
        return SensorTokenRequest.class.isAssignableFrom(clazz);
    }

    /**
     * バリデーション
     */
    @Override
    public void validate(Object request, Errors errors) {
    	SensorTokenRequest sensorTokenRequest = (SensorTokenRequest)request;
    	
    	/**
    	 * errors.rejectValueの第４引数はデフォルトメッセージを指定する。
    	 * 今回のExampleでは利用していないため空文字を指定している。
    	 * 
    	 * 今回は、第２引数にメッセージコードを元にして、例外ハンドラ(RestControllerExceptionHandler)でメッセージ化している。
    	 */
    	
    	// クライアントIDバリデーション
    	if ( Strings.isNullOrEmpty(sensorTokenRequest.getClientId())) {
    		// 必須チェック
    		errors.rejectValue("clientId", "validation.clientId.require");
    	}
    	
    	// シークレットキーバリデーション
    	if ( Strings.isNullOrEmpty(sensorTokenRequest.getSecretKey())) {
    		// 必須チェック
    		errors.rejectValue("secretKey", "validation.secretKey.require");
    	}
    	
    }

}
