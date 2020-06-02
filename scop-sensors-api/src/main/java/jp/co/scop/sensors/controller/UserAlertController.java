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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.scop.common.exception.BusinessException;
import jp.co.scop.common.exception.ValidationException;
import jp.co.scop.sensors.domain.UserAlertRequest;
import jp.co.scop.sensors.domain.validator.UserAlertValidator;
import jp.co.scop.sensors.dto.AlarmContainDto;
import jp.co.scop.sensors.dto.AlarmsDto;
import jp.co.scop.sensors.service.AlarmsService;
import jp.co.scop.sensors.service.NotificationService;

/**
 * 通知
 * @author kanto_mac003
 *
 */
@RestController
@RequestMapping("${system.url}/sensors")
public class UserAlertController {

	/** メッセージソース */
	@Autowired
	MessageSource messageSource;
	/** アクセストークンコントローラー */
	@Autowired
	AuthController authController;
	/** 通知登録サービス */
	@Autowired
	AlarmsService alarmsService;
	/** 通知バリデーション */
	@Autowired
	UserAlertValidator userAlertValidator;
	/** 通知サービス */
	@Autowired
	NotificationService notificationService;
	
	/**
	 * ユーザ通知
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/userAlert")
	public void userAlert(
			@RequestBody(required=false) UserAlertRequest request, BindingResult bindingResult) {

		/**
		 * バリデーションチェックを行う。
		 */
		ValidationUtils.invokeValidator(userAlertValidator, request, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		}
		
		// 登録用Entity作成
		AlarmContainDto dto = alarmsService.generaeteInsertData(request);
		
		// 通知
		notificationService.mobilePush(dto.getAlertNotify());
		
		// 登録実行
		try {
			alarmsService.insert(dto.getRegistTask());
		} catch(Exception e) {
			throw new BusinessException(messageSource.getMessage("global.insertError", null, Locale.JAPAN), e);
		}
		
	}
}
