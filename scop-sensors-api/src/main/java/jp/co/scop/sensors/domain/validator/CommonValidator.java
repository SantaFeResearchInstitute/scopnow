package jp.co.scop.sensors.domain.validator;

import org.springframework.stereotype.Component;

import jp.co.scop.common.util.SensorType;


@Component
public class CommonValidator {

	/**
	 * センサータイプバリデーション
	 * @param requestSensorType
	 * @return 対象センタータイプ有り：FALSE
	 *         対象センタータイプない：TRUE
	 */
    public boolean sensorTypeValidate(String requestSensorType) {
		for ( SensorType data: SensorType.values() ) {
			if ( data.getValue().equals(requestSensorType) ) {
				return false;
			}
		}
		return true;
    }
    
    
}
