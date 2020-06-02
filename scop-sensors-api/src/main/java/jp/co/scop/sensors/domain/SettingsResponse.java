package jp.co.scop.sensors.domain;

import java.sql.Timestamp;

public class SettingsResponse {

	/** シリアルNo */
	private String id;
	/** 呼吸数上限値（bpm） */
	private Integer respiratoryRateUpperLimit;
	/** 呼吸数下限値（bpm） */
	private Integer respiratoryRateLowerLimit;
	/** 心拍数上限値（bpm） */
	private Integer heartRateUpperLimit;
	/** 心拍数下限値（bpm） */
	private Integer heartRateLowerLimit;
	/** 体重 */
	private Integer weight;
	/** 判定時間（秒） */
	private Timestamp judgingTime;
	/** 最終更新時刻 */
	private Timestamp updateDateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getRespiratoryRateUpperLimit() {
		return respiratoryRateUpperLimit;
	}
	public void setRespiratoryRateUpperLimit(Integer respiratoryRateUpperLimit) {
		this.respiratoryRateUpperLimit = respiratoryRateUpperLimit;
	}
	public Integer getRespiratoryRateLowerLimit() {
		return respiratoryRateLowerLimit;
	}
	public void setRespiratoryRateLowerLimit(Integer respiratoryRateLowerLimit) {
		this.respiratoryRateLowerLimit = respiratoryRateLowerLimit;
	}
	public Integer getHeartRateUpperLimit() {
		return heartRateUpperLimit;
	}
	public void setHeartRateUpperLimit(Integer heartRateUpperLimit) {
		this.heartRateUpperLimit = heartRateUpperLimit;
	}
	public Integer getHeartRateLowerLimit() {
		return heartRateLowerLimit;
	}
	public void setHeartRateLowerLimit(Integer heartRateLowerLimit) {
		this.heartRateLowerLimit = heartRateLowerLimit;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Timestamp getJudgingTime() {
		return judgingTime;
	}
	public void setJudgingTime(Timestamp judgingTime) {
		this.judgingTime = judgingTime;
	}
	public Timestamp getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	
}
