package jp.co.scop.common.aws;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.DeleteEndpointRequest;
import com.amazonaws.services.sns.model.DeleteEndpointResult;
import com.amazonaws.services.sns.model.GetEndpointAttributesRequest;
import com.amazonaws.services.sns.model.GetEndpointAttributesResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.scop.sensors.dto.MobilePushDto;

@Component
public class SCOPSNSClient {

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(SCOPSNSClient.class);
	
	/** メッセージソース */
	@Autowired
	MessageSource messageSource;
	
	/** 通知用の設定をapplication.ymlからロードする */
	@Value("${notification.mobile-push.app-arn}")
	String appArn;
	
	@Value("${notification.mobile-push.apns-env}")
	String APNS_ENV;

	/**
	 * いったんシングルトン生成とする
	 */
	private static AmazonSNS snsClient;
	static AmazonSNS getInstance() {
		if(snsClient==null)
			snsClient= AmazonSNSClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1).build();
		return snsClient; 
	}

	public void publish(MobilePushDto mobilePushDto) {

		/**
		 * AWS SNSを利用してモバイルプッシュを行う
		 */
		PublishRequest publishRequest = new PublishRequest();
		publishRequest.setMessageStructure("json");
		publishRequest.setTargetArn(mobilePushDto.getTargetArn());
		publishRequest.setMessage(createMessage(mobilePushDto));
		getInstance().publish(publishRequest);		
		
	}

	String createMessage(MobilePushDto mobilePushDto) {
		/**
		 * appleの通知用フォーマットに従い通知メッセージを生成
		 * 参考：
		 * 　https://qiita.com/sheercat/items/95bd3f41dd83ef66beb1
		 * 　https://docs.aws.amazon.com/ja_jp/sns/latest/dg/mobile-push-send-custommessage.html
		 * 以下のような形式
		 * 開発環境の場合：　　{"APNS_SANDBOX":{¥"aps¥":{¥"alert¥":¥"xxx"}}}
		 * 本番環境の場合：　　{"APNS":{¥"aps¥":{¥"alert¥":¥"xxx"}}}
		 */
		try {	
			
			Map<String, Object> appleMessageMap = new HashMap<String, Object>();
			Map<String, Object> appMessageMap = new HashMap<String, Object>();
			appMessageMap.put("alert", mobilePushDto.getMessage());
			appMessageMap.put("selectUserId", mobilePushDto.getFacilityUserId());
			appMessageMap.put("sound", mobilePushDto.getSoundName());
			appleMessageMap.put("aps", appMessageMap);
		
			/**
			 * 一度、apsの階層をwriteValueAsStringでJSON化した後、APNS_SANDBOXの階層でJSON化する
			 * ダブルクォーテーションをエスケープさせるため。
			 */
			Map<String, Object> messageMap = new HashMap<String, Object>();
			messageMap.put(APNS_ENV, new ObjectMapper().writeValueAsString(appleMessageMap));

			return new ObjectMapper().writeValueAsString(messageMap);
		} catch ( JsonProcessingException e ) {
			throw new RuntimeException(e);
		}
	}
	
	public String createPlatformEndpoint(String deviceToken) {
		/**
		 * iOSが発行したデバイストークンをAWS SNSに登録しエンドポイントを作成する
		 */
		CreatePlatformEndpointRequest cpeReq = new CreatePlatformEndpointRequest();
		cpeReq.setPlatformApplicationArn(appArn);
		cpeReq.setToken(deviceToken);
		CreatePlatformEndpointResult result = getInstance().createPlatformEndpoint(cpeReq);

		return result.getEndpointArn();
	}

	/**
	 * messageからjsonへ変換し、EndpointArnを取得する。
	 * @param message
	 * @return
	 */
	public String getEndpointArn(String message) {
	    try {
			JSONObject json = new JSONObject(message);
			return json.getString("EndpointArn");
		} catch (JSONException e) {
			logger.error(messageSource.getMessage("monitoring.endpoint.error.purse.getString", new Object[]{message}, Locale.JAPAN), e);
		}
	    return null;
	}
	
	/**
	 * endpointArn有効判定
	 * @param endpointArn
	 * @return
	 */
	public boolean getEnabled(String endpointArn) {
		GetEndpointAttributesRequest request = new GetEndpointAttributesRequest().withEndpointArn(endpointArn);
		try {
			GetEndpointAttributesResult attributesResult = getInstance().getEndpointAttributes(request);
			return attributesResult.getAttributes().get("Enabled").equalsIgnoreCase("true");
		} catch (Exception e) {
			logger.error(messageSource.getMessage("monitoring.endpoint.error.get.attributes", new Object[]{endpointArn}, Locale.JAPAN), e);
		} 
		// trueがリターンされた場合、後続処理は行われない。
		return true;
	}
	
	/**
	 * endpointArn削除
	 * @param endpointArn
	 */
	public void deleteEndpoint(String endpointArn) {
		DeleteEndpointRequest request = new DeleteEndpointRequest().withEndpointArn(endpointArn);
		try {
			DeleteEndpointResult deleteEndpointResult = getInstance().deleteEndpoint(request);
			logger.info(messageSource.getMessage("monitoring.endpoint.info.delete", new Object[]{endpointArn}, Locale.JAPAN));
		} catch (Exception e) {
			// TODO スロー
			logger.error(messageSource.getMessage("monitoring.endpoint.error.delete", new Object[]{endpointArn}, Locale.JAPAN), e);
		}
	}	

}
