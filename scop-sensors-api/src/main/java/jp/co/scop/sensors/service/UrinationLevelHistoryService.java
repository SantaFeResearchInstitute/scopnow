package jp.co.scop.sensors.service;

import java.util.List;

import jp.co.scop.common.entity.UrinationLevelHistories;
import jp.co.scop.sensors.domain.UrineAmountRequest;
import jp.co.scop.sensors.dto.UrinationLevelHistoriesDto;
import jp.co.scop.sensors.dto.UrinationLevelsDto;

public interface UrinationLevelHistoryService {


	/**
	 * 
	 * @param historiesDtoList
	 * @param dtoList
	 * @return
	 */
	public int insertUrinationLevelHistories(List<UrinationLevelHistoriesDto> historiesDtoList, List<UrinationLevelsDto> dtoList);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public List<UrinationLevelHistoriesDto> generateInsertData(UrineAmountRequest request);
	
	/**
	 * 
	 * @param request
	 */
	public void insert(UrineAmountRequest request);

	/**
	 * 
	 * @param dtoList
	 */
	public void insert(List<UrinationLevelHistoriesDto> dtoList);
	
	
	
	
}
