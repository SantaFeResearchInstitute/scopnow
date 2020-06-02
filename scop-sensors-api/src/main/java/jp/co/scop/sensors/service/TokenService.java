package jp.co.scop.sensors.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jp.co.scop.sensors.dto.SensorGroupsDto;
import jp.co.scop.sensors.dto.SensorsTokenDto;

public interface TokenService {
	
	/**
	 * センサートークン登録
	 * @param dto 登録対象のDTO
	 * @return 登録件数
	 */
	public int insertSensorsToken(SensorsTokenDto dto);

	/**
	 * JWTを生成する
	 * @param dto JWT生成元情報
	 * @return 生成したJWT 
	 */
	public SensorsTokenDto generate(String cliendId, String secretKey);

	/**
	 * アクセストークンをsubstring(7)したencodedJWT（トークン）を元にシークレットキーとクライアントIDを取得する。
	 * @param encodedJWT
	 * @return
	 */
	public SensorGroupsDto selectByAuthKey(String encodedJWT);
	
	/**
	 * JWTを検証しデコードする
	 * @param encodedJWT エンコードされたJWT
	 * @return デコードされたJWT
	 */
	public DecodedJWT verify(String token);
	
	/**
	 * アルゴリズムを生成する。
	 * @param secretKey
	 * @return
	 */
	public Algorithm algorithm(String secretKey);
	
}
