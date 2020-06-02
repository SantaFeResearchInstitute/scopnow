package jp.co.scop.sensors.service;

import java.util.List;

import jp.co.scop.common.entity.Sensors;
import jp.co.scop.sensors.domain.SettingsRequest;
import jp.co.scop.sensors.dto.SensorsDto;

public interface SensorsService {
	
	/**
	 * 
	 * @param dtoList
	 */
	public void insert(List<SensorsDto> dtoList);
	
	/**
	 * センサーのシリアルナンバーを登録するのデータを作成する。
	 * @param request
	 * @return
	 */
	public List<SensorsDto> generateInsertData(SettingsRequest request);

}
