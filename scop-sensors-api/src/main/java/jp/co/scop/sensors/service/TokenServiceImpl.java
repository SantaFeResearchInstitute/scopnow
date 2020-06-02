package jp.co.scop.sensors.service;

import java.security.AccessControlException;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Objects;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jp.co.scop.common.dao.SensorGroupsDao;
import jp.co.scop.common.dao.SensorMakerAuthenticationsDao;
import jp.co.scop.common.entity.CustomSensorMakerAuthentications;
import jp.co.scop.common.entity.SensorGroups;
import jp.co.scop.common.entity.SensorMakerAuthentications;
import jp.co.scop.common.exception.BusinessException;
import jp.co.scop.common.util.SecurityProperty;
import jp.co.scop.common.util.ServerProperty;
import jp.co.scop.sensors.dto.SensorGroupsDto;
import jp.co.scop.sensors.dto.SensorsTokenDto;

@Service
public class TokenServiceImpl implements TokenService{

	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
	
	/** サーバープロパティ */
	@Autowired
	ServerProperty serverProperty;
	
	/** セキュリティプロパティ */
	@Autowired
	SecurityProperty securityProperty;
	
	/** センサーメーカー認証Dao */
	@Autowired
	SensorMakerAuthenticationsDao sensorMakerAuthenticationsDao;
	
	/** センサー管理Dao */
	@Autowired
	SensorGroupsDao sensorGroupsDao;

	/** メッセージソース */
	@Autowired
	MessageSource messageSource;
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional
	public int insertSensorsToken(SensorsTokenDto dto) {
		
		SensorMakerAuthentications entity = new SensorMakerAuthentications();
		
		entity.setCompanyId(dto.getCompanyId());
		entity.setSensorGroupId(dto.getSensorGroupId());
		entity.setSensorMakerAuthenticationUuid(dto.getSensorMakerAuthenticationUuid());
		entity.setAuthKey(dto.getAuthKey());
		entity.setIssuer(dto.getIssuer());
		entity.setExpirationDate(dto.getExpirationDate());
		entity.setSensorGroupId(dto.getSensorGroupId());
		entity.setCreateDate(dto.getCreateDate());
		
		int result = sensorMakerAuthenticationsDao.insert(entity);
		
		return result;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public SensorsTokenDto generate(String cliendId, String secretKey) {
		
		SensorGroups sensorGroups = sensorGroupsDao.selectByClientIdAndSecretKey(cliendId, secretKey);
		
		if( Objects.isNull(sensorGroups) ) {
			throw new AccessControlException(messageSource.getMessage("security.access.denied", new Object[]{cliendId, secretKey}, Locale.JAPAN));
		}
		
		String uuid = serverProperty.generateUUID();
		Algorithm algorithm = algorithm(secretKey);
		Timestamp issuedAt = new Timestamp(System.currentTimeMillis());
		Timestamp expiresAt = new Timestamp(issuedAt.getTime() + securityProperty.getExpireMilliSec());
		String sub = jwtSubjcet(uuid, cliendId, issuedAt);
		
		String token = JWT.create()
				.withIssuer(securityProperty.getIssuer())	//"iss" : Issuer 発行者
				.withSubject(sub)                           //"sub" : Subject
				.withIssuedAt(issuedAt)      			    //"iat" : Issued At　発行日
				.withExpiresAt(expiresAt)    			    //"exp" : Expiration Time　有効終了日
				.sign(algorithm);

	    logger.debug("JWTを生成しました : {}", token);
		
		// トークン登録データ
		SensorsTokenDto dto = new SensorsTokenDto();
		dto.setCompanyId(sensorGroups.getCompanyId());
		dto.setSensorGroupId(sensorGroups.getSensorGroupId());;
		dto.setSensorMakerAuthenticationUuid(uuid);
		dto.setIssuer(serverProperty.getServerId());
		dto.setAuthKey(token);
		dto.setCreateDate(issuedAt);
		dto.setExpirationDate(expiresAt);
		
		return dto;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DecodedJWT verify(String encodedJWT) {

		/**
		 * JWTからセンサーグループを取得する
		 */
		SensorGroupsDto dto = selectByAuthKey(encodedJWT);

		Algorithm algorithm = algorithm(dto.getSecretKey());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(encodedJWT);
	}

	/**
	 * {@inheritDoc}
	 */
	public SensorGroupsDto selectByAuthKey(String encodedJWT) {
		
		CustomSensorMakerAuthentications entity = sensorMakerAuthenticationsDao.selectByAuthKey(encodedJWT);

		SensorGroupsDto dto = new SensorGroupsDto();

		if( Objects.isNull(entity) ) {
			throw new AccessControlException("security.token.nonRegist");
		}

		dto.setSecretKey(entity.getSecretKey());
		dto.setClientId(entity.getClientId());
		
		return dto;
	}

	/**
	 * アルゴリズムを生成する。
	 * @param secretKey
	 * @return
	 */
	public Algorithm algorithm(String secretKey) {
		Algorithm algorithm = null;
		try {
			algorithm = Algorithm.HMAC256(secretKey);
		} catch (Exception e) {
			throw new BusinessException("security.secretKey.invalid", e);
		}
		return algorithm;
	}
	

    private static final String UNDER_SCORE="_";
    /**
	 * jwt subject作成
     * @param uuid
     * @param cliendId
     * @param issuedAt
     * @return
     */
	private String jwtSubjcet(String uuid, String cliendId, Timestamp issuedAt) {
    	return new StringBuilder(uuid).append(UNDER_SCORE).
    			append(cliendId).append(UNDER_SCORE).append(issuedAt.toString()).toString();
	}

}
