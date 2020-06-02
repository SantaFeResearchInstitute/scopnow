package jp.co.scop.sensors.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jp.co.scop.common.dao.SensorGroupsDao;
import jp.co.scop.common.entity.SensorGroups;
import jp.co.scop.sensors.dto.SensorGroupsDto;

@Service
public class SensorGroupsServiceImpl implements SensorGroupsService{
	
	@Autowired 
	SensorGroupsDao sensorGroupsDao;  

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean searchBy(String clientId, String secretKey) {
		
		SensorGroups sensorGroups = sensorGroupsDao.selectByClientIdAndSecretKey(clientId, secretKey);
		
		return Objects.isNull(sensorGroups);
		
	}

}
