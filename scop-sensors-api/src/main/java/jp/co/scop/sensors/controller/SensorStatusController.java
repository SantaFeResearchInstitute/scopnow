package jp.co.scop.sensors.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.scop.common.exception.BusinessException;
import jp.co.scop.common.exception.ValidationException;
import jp.co.scop.sensors.domain.SensorStatusRequest;
import jp.co.scop.sensors.domain.validator.SensorStatusValidator;
import jp.co.scop.sensors.dto.SensorStatusesDto;
import jp.co.scop.sensors.service.SensorStatusesService;
import jp.co.scop.sensors.service.TokenService;



/**
 * センサー状態更新
 */
@RestController
@RequestMapping("${system.url}/sensors")
public class SensorStatusController {

	/** メッセージソース */
	@Autowired
	MessageSource messageSource;
	
	/** センサー状態サービス */
	@Autowired 
	SensorStatusesService sensorStatusesService;
	
	/** センサー状態バリデーション */
	@Autowired
	SensorStatusValidator sensorStatusValidator;

	/** トークンサービス */
	@Autowired
	TokenService tokenService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/sensorStatus")
	public void sensorStatus(
			@RequestBody SensorStatusRequest request, BindingResult bindingResult) {

    	/**
    	 * バリデーションチェックを行う。
    	 */
		ValidationUtils.invokeValidator(sensorStatusValidator, request, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        
        List<SensorStatusesDto> dtoList = sensorStatusesService.generateInsertData(request);

		// 登録
		try {
			sensorStatusesService.deleteInsert(dtoList);
		} catch(Exception e) {
			throw new BusinessException(messageSource.getMessage("global.insertError", null, Locale.JAPAN), e);
		}
		
	}

}
