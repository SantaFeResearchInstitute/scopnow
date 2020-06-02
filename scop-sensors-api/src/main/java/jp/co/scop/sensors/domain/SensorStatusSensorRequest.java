package jp.co.scop.sensors.domain;

import java.sql.Timestamp;

public class SensorStatusSensorRequest {

	private String id;
	private Integer sensorStatusCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getSensorStatusCode() {
		return sensorStatusCode;
	}
	public void setSensorStatusCode(Integer sensorStatusCode) {
		this.sensorStatusCode = sensorStatusCode;
	}

}

