package jp.co.scop.sensors.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jp.co.scop.common.exception.BusinessException;
import jp.co.scop.common.exception.ValidationException;
import jp.co.scop.sensors.domain.SensorTokenRequest;
import jp.co.scop.sensors.domain.SensorTokenResponse;
import jp.co.scop.sensors.domain.validator.SensorTokenIssueValidator;
import jp.co.scop.sensors.dto.SensorsTokenDto;
import jp.co.scop.sensors.service.TokenService;


@RestController
@RequestMapping("${system.url}/auth")
public class AuthController {

	
	/** メッセージソース */
	@Autowired
	MessageSource messageSource;

	/** トークンサービス */
	@Autowired
	TokenService tokenService;
	
	/** トークン発行バリデータ */
	@Autowired
	SensorTokenIssueValidator sensorTokenIssueValidator;
	
	
	/**
	 * センサー機器アクセストークン発行
	 * @param conditionRequest 検索条件用リクエスト
	 * @param bindingResult バインディングリザルト(バリデーション結果を詰めるオブジェクト)
	 * return アクセストークン
	 */
	@PostMapping("/signIn")
	public SensorTokenResponse signIn(
			@RequestBody SensorTokenRequest request, BindingResult bindingResult) {
		
    	SensorTokenResponse response = new SensorTokenResponse();
		
    	/**
    	 * バリデーションチェックを行う。
    	 */
		ValidationUtils.invokeValidator(sensorTokenIssueValidator, request, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

		SensorsTokenDto dto = tokenService.generate(request.getClientId(), request.getSecretKey());
		
		// 登録結果
		try {
			tokenService.insertSensorsToken(dto);
		} catch (Exception e) {
			throw new BusinessException(messageSource.getMessage("global.insertError", null, Locale.JAPAN), e);
		}
		
    	// レスポンス格納
		response.setToken(dto.getAuthKey());
		
		return response;
	
	}
	
}
