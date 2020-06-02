package jp.co.scop.sensors.domain;

import java.util.List;

public class SettingsRequest {
	
	/** クライアントID:中継サーバ（管理PC） */
	private String clientId;
	/** 機器種別 */
	private String sensorType;
	/** センサー配列 */
	private List<SettingsSensorsRequest> sensors;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSensorType() {
		return sensorType;
	}
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}
	public List<SettingsSensorsRequest> getSensors() {
		return sensors;
	}
	public void setSensors(List<SettingsSensorsRequest> sensors) {
		this.sensors = sensors;
	}

}
