package jp.co.scop.sensors.domain;

import java.util.List;


public class BatteryChargerRequest {

	private String sensorType;
	
	private List<BatteryChargerSensorRequest> sensors;
	
	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public List<BatteryChargerSensorRequest> getSensors() {
		return sensors;
	}

	public List<BatteryChargerSensorRequest> setSensors(List<BatteryChargerSensorRequest> sensors) {
		this.sensors = sensors;
		return sensors;
	}
}

