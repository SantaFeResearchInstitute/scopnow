package jp.co.scop.sensors.service;

import java.util.List;

import jp.co.scop.sensors.domain.UserAlertRequest;
import jp.co.scop.sensors.dto.AlarmContainDto;
import jp.co.scop.sensors.dto.AlarmsDto;

public interface AlarmsService {

	
	/**
	 * 通知内容を登録する
	 * @param dto 登録対象のDTO
	 * @return 登録件数
	 * @throws Exception 
	 */
	public void insert(List<AlarmsDto> dtoList);
	
	/**
	 * 通知内容を登録するデータを作成する
	 * @param facilityUsers
	 * @param request
	 * @return 登録データ
	 */
	public AlarmContainDto generaeteInsertData(UserAlertRequest request);
	

}
