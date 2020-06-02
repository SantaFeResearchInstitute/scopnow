package jp.co.scop.sensors.domain;

import java.sql.Timestamp;
import java.util.List;

public class UrineAmountSensorRequest {

	private String id;
	private Integer urinationAmount;
	private Timestamp dateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getUrinationAmount() {
		return urinationAmount;
	}
	public void setUrinationAmount(Integer urinationAmount) {
		this.urinationAmount = urinationAmount;
	}
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

}

