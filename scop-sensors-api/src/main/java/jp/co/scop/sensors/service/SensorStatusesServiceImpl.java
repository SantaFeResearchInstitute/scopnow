package jp.co.scop.sensors.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.scop.common.dao.FacilityUserManagementsDao;
import jp.co.scop.common.dao.SensorStatusesDao;
import jp.co.scop.common.dao.SensorsDao;
import jp.co.scop.common.entity.CustomFacilityUserManagementsBySerialNo;
import jp.co.scop.common.entity.SensorStatuses;
import jp.co.scop.common.entity.Sensors;
import jp.co.scop.common.util.CommonConstants;
import jp.co.scop.common.util.ServerProperty;
import jp.co.scop.common.util.TimeUtil;
import jp.co.scop.sensors.domain.SensorStatusRequest;
import jp.co.scop.sensors.domain.SensorStatusSensorRequest;
import jp.co.scop.sensors.dto.FacilityUserManagementsSearchConditionDto;
import jp.co.scop.sensors.dto.SensorStatusesDto;

@Service
public class SensorStatusesServiceImpl implements SensorStatusesService{


	/** メッセージソース */
	@Autowired
	MessageSource messageSource;
	
	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(SensorStatusesServiceImpl.class);
	
	@Autowired
	SensorStatusesDao sensorStatusesDao;

	@Autowired
	FacilityUserManagementsDao facilityUserManagementsDao;
	
	@Autowired
	ServerProperty serverProperty;

	/** センサーDao */
	@Autowired
	SensorsDao sensorsDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SensorStatusesDto> generateInsertData(SensorStatusRequest request) {
		
		List<SensorStatusesDto> dtoList = new ArrayList<SensorStatusesDto>();
		
		for (SensorStatusSensorRequest data :request.getSensors()) {

			Sensors sensors = sensorsDao.selectBySensorTypeSerialNo(request.getSensorType(), data.getId());
			if( Objects.isNull(sensors)){
				// センサー登録チェック
				logger.info(messageSource.getMessage("invalid.sensor", new Object[]{request.getSensorType(), data.getId()}, Locale.JAPAN));
				continue;
			}
			
			SensorStatusesDto dto = new SensorStatusesDto();
			FacilityUserManagementsSearchConditionDto condition = new FacilityUserManagementsSearchConditionDto();
			condition.setDate(TimeUtil.sysdate());
			condition.setSensorType(request.getSensorType());
			condition.setSerialNo(data.getId());
			
			/**
			 * 利用者センサーテーブルより企業ID、利用者ID、利用者センサーIDを取得する。
			 * 登録されていない場合はNULLで登録を行う。
			 */
			CustomFacilityUserManagementsBySerialNo facilityUserManagement = facilityUserManagementsDao.selectBySerialNo(condition);
			
			/** 企業ID */			
			dto.setCompanyId( Objects.isNull(facilityUserManagement) ? null : facilityUserManagement.getCompanyId() );
			/** 利用者ID */
			dto.setFacilityUserId( Objects.isNull(facilityUserManagement) ? null : facilityUserManagement.getFacilityUserId() );
			/** 利用者センサーID */
			dto.setFacilityUserManagementId( Objects.isNull(facilityUserManagement) ? null : facilityUserManagement.getFacilityUserManagementId() );
			/** センサータイプ */
			dto.setSensorType(request.getSensorType());
			/** シリアルナンバー */
			dto.setSerialNo(data.getId());
			/** 稼働状況 */
			dto.setOperatingStatus(data.getSensorStatusCode());
			/** 作成日時 */
			dto.setCreateDate(TimeUtil.sysdate());
			/** 作成者 */
			dto.setCreateUser(serverProperty.getServerId());
			/** 更新日時 */
			dto.setUpdateDate(TimeUtil.sysdate());
			/** 更新者 */
			dto.setUpdateUser(serverProperty.getServerId());
			/** 削除フラグ */
			dto.setDeleteFlg(CommonConstants.DeleteFlg.DEFAULT.getValue());
			
			dtoList.add(dto);
			
		}
		
		return dtoList;
	}



	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void deleteInsert(List<SensorStatusesDto> dtoList) {
		
		for(SensorStatusesDto dto : dtoList) {
			
			SensorStatuses entity = new SensorStatuses();

			/** 企業ID */			
			entity.setCompanyId(dto.getCompanyId());
			/** 利用者ID */
			entity.setFacilityUserId(dto.getFacilityUserId());
			/** 利用者センサーID */
			entity.setFacilityUserManagementId(dto.getFacilityUserManagementId());
			/** センサータイプ */
			entity.setSensorType(dto.getSensorType());
			/** シリアルナンバー */
			entity.setSerialNo(dto.getSerialNo());
			/** 稼働状況 */
			entity.setOperatingStatus(dto.getOperatingStatus());
			/** 作成日時 */
			entity.setCreateDate(dto.getCreateDate());
			/** 作成者 */
			entity.setCreateUser(dto.getCreateUser());
			/** 更新日時 */
			entity.setUpdateDate(dto.getUpdateDate());
			/** 更新者 */
			entity.setUpdateUser(dto.getUpdateUser());
			/** 削除フラグ */
			entity.setDeleteFlg(dto.getDeleteFlg());

			sensorStatusesDao.deleteBySerialNo(entity);
			sensorStatusesDao.insert(entity);
			
		}	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public void deleteInsert(SensorStatusRequest request) {
		
		for (SensorStatusSensorRequest data :request.getSensors()) {
			
			SensorStatuses entity = new SensorStatuses();

			/**
			 * 利用者センサーテーブルより企業ID、利用者ID、利用者センサーIDを取得する。
			 * 登録されていない場合はNULLで登録を行う。
			 */
			FacilityUserManagementsSearchConditionDto condition = new FacilityUserManagementsSearchConditionDto();
			condition.setDate(TimeUtil.sysdate());
			condition.setSensorType(request.getSensorType());
			condition.setSerialNo(data.getId());
			
			/**
			 * 利用者センサーテーブルより企業ID、利用者ID、利用者センサーIDを取得する。
			 * 登録されていない場合はNULLで登録を行う。
			 */
			CustomFacilityUserManagementsBySerialNo facilityUserManagement = facilityUserManagementsDao.selectBySerialNo(condition);

			/** 企業ID */			
			entity.setCompanyId( Objects.isNull(facilityUserManagement) ? null : facilityUserManagement.getCompanyId() );
			/** 利用者ID */
			entity.setFacilityUserId( Objects.isNull(facilityUserManagement) ? null : facilityUserManagement.getFacilityUserId() );
			/** 利用者センサーID */
			entity.setFacilityUserManagementId( Objects.isNull(facilityUserManagement) ? null : facilityUserManagement.getFacilityUserManagementId() );
			/** センサータイプ */
			entity.setSensorType(request.getSensorType());
			/** シリアルナンバー */
			entity.setSerialNo(data.getId());
			/** 稼働状況 */
			entity.setOperatingStatus(data.getSensorStatusCode());
			/** 作成日時 */
			entity.setCreateDate(TimeUtil.sysdate());
			/** 作成者 */
			entity.setCreateUser(serverProperty.getServerId());
			/** 更新日時 */
			entity.setUpdateDate(TimeUtil.sysdate());
			/** 作成者 */
			entity.setUpdateUser(serverProperty.getServerId());
			/** 削除フラグ */
			entity.setDeleteFlg(CommonConstants.DeleteFlg.DEFAULT.getValue());

			sensorStatusesDao.deleteBySerialNo(entity);
			sensorStatusesDao.insert(entity);
			
		}	
	}
}
