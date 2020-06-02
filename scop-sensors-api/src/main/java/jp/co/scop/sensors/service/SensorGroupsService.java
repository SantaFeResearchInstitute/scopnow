package jp.co.scop.sensors.service;

import jp.co.scop.sensors.dto.SensorGroupsDto;

public interface SensorGroupsService {

	/**
	 * センサー管理をIDを元に検索する
	 * @return センサー管理情報
	 */
	public boolean searchBy(String sensorGroupInternalId, String secretKey); 
	

}
