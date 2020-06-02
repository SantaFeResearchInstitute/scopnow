package jp.co.scop.sensors.service;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import jp.co.scop.sensors.dto.AlarmsDto;
import jp.co.scop.sensors.dto.AlertNotifyDto;

/**
 * 通知用サービスインターフェース
 */
public interface NotificationService {

	/**
	 * メッセージをモバイルプッシュする
	 * @param sensorDto センサーデータ
	 */
	public void mobilePush(List<AlertNotifyDto> dtoList);
	
	/**
	 * 無効になっているendpointarnをaws、db上から削除する。 
	 * @param input
	 */
	public void handleRequest(SNSEvent input);
}
