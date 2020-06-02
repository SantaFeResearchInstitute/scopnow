package jp.co.scop.sensors.domain;

import java.util.List;


public class SensorStatusRequest {

	private String sensorType;
	
	private List<SensorStatusSensorRequest> sensors;
	
	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public List<SensorStatusSensorRequest> getSensors() {
		return sensors;
	}

	public List<SensorStatusSensorRequest> setSensors(List<SensorStatusSensorRequest> sensors) {
		this.sensors = sensors;
		return sensors;
	}
}

