package jp.co.scop.sensors.domain;

public class SensorTokenRequest {
	/** クライアントID */
	public String clientId;
	/** シークレット */
	public String secretKey;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
