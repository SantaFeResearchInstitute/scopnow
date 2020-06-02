package jp.co.scop.sensors.service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord;
import com.google.common.base.Strings;

import jp.co.scop.common.aws.SCOPSNSClient;
import jp.co.scop.common.dao.DeviceTokensDao;
import jp.co.scop.common.dao.EndPointArnsDao;
import jp.co.scop.common.dao.ScopNowAppLoginsDao;
import jp.co.scop.common.entity.CutomEndPointARN;
import jp.co.scop.common.entity.DeviceTokens;
import jp.co.scop.common.entity.EndPointArns;
import jp.co.scop.common.entity.ScopNowAppLogins;
import jp.co.scop.common.util.LogUtility;
import jp.co.scop.common.util.CommonConstants.NullStatus;
import jp.co.scop.sensors.dto.AlarmsDto;
import jp.co.scop.sensors.dto.AlertNotifyDto;
import jp.co.scop.sensors.dto.MobilePushDto;

/**
 * 通知用サービスクラス
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	/** AamazonSNSクライアント */
	@Autowired
	SCOPSNSClient scopSNSClient;
	/** メッセージソース */
	@Autowired
	MessageSource messageSource;
	/** ログイン状態DAO */
	@Autowired
	EndPointArnsDao endPointArnsDao;
	/** デバイストークンDAO */
	@Autowired
	DeviceTokensDao deviceTokensDao;
	/** SCOPNowログイン状態 */
	@Autowired
	ScopNowAppLoginsDao scopNowAppLoginsDao;
	@Autowired
	LogUtility logUtility;

	/**
	 * {@inheritDoc}
	 */
	public void mobilePush(List<AlertNotifyDto> dtoList) {
		
		for(AlertNotifyDto dto : dtoList) {
			if( Objects.isNull(dto.getLastName()) || NullStatus.EMPTY.getString().equals(dto.getBedName())) {
				// 利用者に紐づいていないタスクは通知しない。
				logger.info(messageSource.getMessage("task.non.alarm", new Object[]{dto.getSensorType(), dto.getSerialNo()}, Locale.JAPAN));
				continue;
			}

			/**
			 * 通知を元に通知するデバイス、職員を決める。
			 */
			// ログインしているスタッフを取得する。
			List<CutomEndPointARN> scopNowAppLoginsList = endPointArnsDao.selectByfacilityUserGroups(dto.getCompanyId(), dto.getGroupId());
			if( Objects.isNull(scopNowAppLoginsList) || scopNowAppLoginsList.size() == 0) {
				// ログインしているスタッフがいなければリターン
				logger.info(messageSource.getMessage("task.staff.noAlarm", null, Locale.JAPAN));
				return;
			}
			
			for(CutomEndPointARN entity : scopNowAppLoginsList) {

				/**
				 * AWS SNSを利用してモバイルプッシュを行う
				 */
				String msg = messageSource.getMessage("task.common.message", new Object[]{dto.getBedName(), dto.getLastName(), dto.getAlarmsContent()}, Locale.JAPAN);
				try {
					
					MobilePushDto mobilePushDto = new MobilePushDto();
					mobilePushDto.setFacilityUserId(dto.getFacilityUserId());
					mobilePushDto.setTargetArn(entity.getEndPointArn());
					mobilePushDto.setMessage(msg);
					mobilePushDto.setSoundName(dto.getSoundName());
					
					if (!scopSNSClient.getEnabled(entity.getEndPointArn())) {
						// エンドポイントが有効でない時通知しない。
						logger.info(messageSource.getMessage("task.non.alarm.endpointarn.disabled", new Object[]{
								logUtility.array2JsonLogging(entity), logUtility.array2JsonLogging(dto)}, Locale.JAPAN));
						continue;
					}
					scopSNSClient.publish(mobilePushDto);
				} catch (Exception e) {
					logger.error("モバイルプッシュ失敗しました。DevicesToken＝" + entity.getDeviceToken() + "。EndPointARN＝" + entity.getEndPointArn() + "。");
					logger.error(e.getMessage());
					continue;
				}

				logger.info("モバイルプッシュ対象のEndPointARN＝" + entity.getEndPointArn());
				logger.info("モバイルプッシュしました。メッセージ=" + msg);
			}	
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void handleRequest(SNSEvent input) {
		for (SNSRecord record : input.getRecords()) {
			handle(record);
		}
	}
	
	/**
	 * 
	 * @param record
	 */
	private void handle(SNSRecord record) {
		
	    // JSONテキスト
		String message = record.getSNS().getMessage();
		if (message == null) {
			logger.info(messageSource.getMessage("monitoring.endpoint.info.sns.message.null", new Object[]{record.getSNS()}, Locale.JAPAN));
			return;
		}
		
		// EndpointArn取得
		String endpointArn = scopSNSClient.getEndpointArn(message);
		if (endpointArn == "null" || Strings.isNullOrEmpty(endpointArn)) {
			logger.info(messageSource.getMessage("monitoring.endpoint.info.sns.message.endpointArn.null", new Object[]{record.getSNS()}, Locale.JAPAN));
			return;
		}
		
		if (!scopSNSClient.getEnabled(endpointArn)) {
			// DBから削除
			deleteEndpointArnFromDB(endpointArn);
			// AWSから削除
			scopSNSClient.deleteEndpoint(endpointArn);
		}
		
	}
	
	/**
	 * DBのEndpointArn、デバイストークン、ログイン状態のレコードを削除する。
	 * @param endpointArn
	 */
	private void deleteEndpointArnFromDB(String endpointArn) {
		
		// EndPointArn取得
		EndPointArns endPointArns = endPointArnsDao.selectByEndPointArn(endpointArn);
		if (Objects.nonNull(endPointArns)) {

			// EndPointArn削除
			endPointArnsDao.delete(endPointArns);

			// デバイストークン取得
			DeviceTokens deviceTokens = deviceTokensDao.selectById(endPointArns.getDeviceTokenUuid());

			if (Objects.nonNull(deviceTokens)) {

				// デバイストークン削除
				deviceTokensDao.delete(deviceTokens);

				// ログイン状態取得
				ScopNowAppLogins scopNowAppLogins = scopNowAppLoginsDao.selectByUUID(deviceTokens.getDeviceTokenUuid(), endPointArns.getEndPointArnUuid());
				if (Objects.nonNull(scopNowAppLogins)) {
					// ログイン状態削除
					scopNowAppLoginsDao.delete(scopNowAppLogins);
				} // delete ScopNowAppLogins
			} // delete deviceToken
		} // delete db endPointArns
	}
}
