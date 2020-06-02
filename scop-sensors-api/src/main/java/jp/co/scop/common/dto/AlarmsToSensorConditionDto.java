package jp.co.scop.common.dto;

import java.time.LocalDateTime;

/**
 * 通知検索条件
 * @author kanto_mac003
 *
 */
public class AlarmsToSensorConditionDto {
	
	/** センサータイプ */
	private String sensorType;
	
	/** シリアルナンバー */
	private String serialNumber;
	
	/** 通知コード */
	private Integer alarmCode;
	
	/** システム日時 */
	private LocalDateTime localDateTime;

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(Integer alarmCode) {
		this.alarmCode = alarmCode;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

}
