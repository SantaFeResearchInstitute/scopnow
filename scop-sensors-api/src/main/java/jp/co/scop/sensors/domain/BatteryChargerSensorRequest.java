package jp.co.scop.sensors.domain;


public class BatteryChargerSensorRequest {

	private String id;
	private Integer charge;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCharge() {
		return charge;
	}
	public void setCharge(Integer charge) {
		this.charge = charge;
	}

}

