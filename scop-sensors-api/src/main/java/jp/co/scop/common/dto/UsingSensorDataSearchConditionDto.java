package jp.co.scop.common.dto;

import java.sql.Timestamp;

/**
 * 現在利用中のセンサーデータ検索条件DTO
 * @author ncxxsl-koseki
 *
 */
public class UsingSensorDataSearchConditionDto {

	/** 企業ID */
	private Integer companyId;
	/** センサータイプ */
	private String sensorType;
	/** システム日時 */
	private Timestamp systemDate;
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getSensorType() {
		return sensorType;
	}
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}
	public Timestamp getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(Timestamp systemDate) {
		this.systemDate = systemDate;
	}

}
