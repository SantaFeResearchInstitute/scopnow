package jp.co.scop.sensors.domain;

import java.util.List;

public class UserAlertRequest {
	
	private String sensorType;
	
	private List<UserAlertSensorsRequest> sensors;

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public List<UserAlertSensorsRequest> getSensors() {
		return sensors;
	}

	public void setSensors(List<UserAlertSensorsRequest> sensors) {
		this.sensors = sensors;
	}

}
