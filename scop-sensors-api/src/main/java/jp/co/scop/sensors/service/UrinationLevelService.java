package jp.co.scop.sensors.service;

import java.util.List;

import jp.co.scop.common.entity.UrinationLevels;
import jp.co.scop.sensors.domain.UrineAmountRequest;
import jp.co.scop.sensors.dto.UrinationLevelsDto;

public interface UrinationLevelService {


	/**
	 * 
	 * @param request
	 * @return
	 */
	public List<UrinationLevelsDto> insertData(UrineAmountRequest request);

	/**
	 * 作成したdtoをentityに詰め替えてインサート
	 * 
	 * @param dtoList
	 */
	public void deleteInsert(List<UrinationLevelsDto> dtoList);
	
	/**
	 * リクエストから直接entityへ詰め込みインサート
	 * @param UrineAmountRequest
	 * @return
	 */
	public void deleteInsert(UrineAmountRequest request);
	
	

	
}
