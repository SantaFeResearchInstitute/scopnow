package jp.co.scop.sensors.domain;

import java.util.List;


public class UrineAmountRequest {

	private String sensorType;
	
	private List<UrineAmountSensorRequest> sensors;
	
	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public List<UrineAmountSensorRequest> getSensors() {
		return sensors;
	}

	public List<UrineAmountSensorRequest> setSensors(List<UrineAmountSensorRequest> sensors) {
		this.sensors = sensors;
		return sensors;
	}
}

