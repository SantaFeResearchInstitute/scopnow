package jp.co.scop.sensors.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.scop.common.dao.ProductsDao;
import jp.co.scop.common.dao.SensorGroupsDao;
import jp.co.scop.common.dao.SensorsDao;
import jp.co.scop.common.entity.Products;
import jp.co.scop.common.entity.SensorGroups;
import jp.co.scop.common.entity.Sensors;
import jp.co.scop.common.exception.BusinessException;
import jp.co.scop.common.exception.ValidationException;
import jp.co.scop.common.util.CommonConstants;
import jp.co.scop.common.util.ServerProperty;
import jp.co.scop.common.util.TimeUtil;
import jp.co.scop.sensors.domain.SettingsRequest;
import jp.co.scop.sensors.domain.SettingsSensorsRequest;
import jp.co.scop.sensors.dto.SensorGroupsDto;
import jp.co.scop.sensors.dto.SensorsDto;

@Service
public class SensorsServiceImpl implements SensorsService{
	
	/** センサーグループ */
	@Autowired
	SensorsDao sensorsDao;

	/** センサーグループ管理 */
	@Autowired 
	SensorGroupsDao sensorGroupsDao;

	/** メッセージソース */
	@Autowired
	MessageSource messageSource;

	/** サーバープロパティ */
	@Autowired
	ServerProperty serverProperty;
	
	/** 製品Dao */
	@Autowired
	ProductsDao productsDao;
	
	/**
	 * センサーテーブル一括登録
	 */
	@Transactional
	public int[] insertSensorsSettings(List<Sensors> sensors) {
		
		int[] result = sensorsDao.insert(sensors);
		
		return result;
	}

	/**
	 * 
	 */
	@Transactional
	public void insert(List<SensorsDto> dtoList) {
		
		for(SensorsDto dto : dtoList) {

			Sensors sensors = sensorsDao.selectBySerialNo(dto.getProductId(), dto.getSerialNo());
			if( Objects.isNull(sensors)) {
				
				Sensors entity = new Sensors();
				
				entity.setCompanyId(dto.getCompanyId());
				entity.setSensorId(sensorsDao.execute(dto.getCompanyId()));
				entity.setProductId(dto.getProductId());
				entity.setSerialNo(dto.getSerialNo());
				entity.setIntroductionDate(dto.getIntroductionDate());
				entity.setRemarks(null);
				entity.setCreateDate(dto.getCreateDate());
				entity.setCreateUser(dto.getCreateUser());
				entity.setUpdateDate(dto.getUpdateDate());
				entity.setUpdateUser(dto.getUpdateUser());
				entity.setDeleteFlg(dto.getDeleteFlg());
				
				sensorsDao.insert(entity);
				
			} else {
				
				sensors.setUpdateDate(dto.getUpdateDate());
				sensors.setUpdateUser(dto.getUpdateUser());
				
				sensorsDao.update(sensors);
				
			}
		}
	}

	/**
	 * 
	 */
	@Transactional
	public List<SensorsDto> generateInsertData(SettingsRequest request) {

		List<SensorsDto> dtoList = new ArrayList<SensorsDto>();
		// センサー管理情報取得
		SensorGroups sensorGroups = sensorGroupsDao.selectBySensorClientId(request.getClientId());
		if(Objects.isNull(sensorGroups)) {
			throw new BusinessException("validation.clientId.search");
		}

		// 製品取得
		Products products = productsDao.selectBySensorType(request.getSensorType());
		if( Objects.isNull(products) ) {
			throw new BusinessException("validation.product.search");
		}
		
		// 登録データ格納
		for(SettingsSensorsRequest data: request.getSensors()) {
			
			SensorsDto dto = new SensorsDto();
			
			Timestamp sysdate = TimeUtil.sysdate();
			String id = charaCombin(sensorGroups.getCompanyId(), serverProperty.getServerId());

			/** 企業ID */
			dto.setCompanyId(sensorGroups.getCompanyId());
			/** 製品ID */
			dto.setProductId(products.getProductId());
			/** センサータイプ */
			dto.setSensorType(request.getSensorType());
			/** シリアルナンバー */
			dto.setSerialNo(data.getId());
			/** 導入日 */
			dto.setIntroductionDate(sysdate);
			/** 作成日時 */
			dto.setCreateDate(sysdate);
			/** 作成者 */
			dto.setCreateUser(id);
			/** 更新日時 */
			dto.setUpdateDate(sysdate);
			/** 更新者 */
			dto.setUpdateUser(id);
			/** 削除フラグ */
			dto.setDeleteFlg(CommonConstants.DeleteFlg.DEFAULT.getValue());
			
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param id
	 * @return
	 */
	private String charaCombin(Integer id, Integer id2) {
		return id.toString() + '@' + id2.toString();
	}
	
	
}
