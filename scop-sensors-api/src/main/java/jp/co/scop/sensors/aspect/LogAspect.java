package jp.co.scop.sensors.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	
    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * コントローラーメソッド開始時のログ出力処理
     * @param joinPoint
     */
    @Before("execution(* jp.co.scop.sensors.controller..*Controller.*(..))")
    public void invokeApiControllerBefore(JoinPoint joinPoint) {
        outputStartLog(joinPoint);
    }
    private void outputStartLog(JoinPoint joinPoint) {
        String logMessage = "[START] " + getClassName(joinPoint) + "." + getSignatureName(joinPoint);
        logger.info(logMessage);
    }
    /**
     * コントローラーメソッド終了時のログ出力処理
     * @param joinPoint
     */
    @After("execution(* jp.co.scop.sensors.controller..*Controller.*(..))")
    public void invokeControllerAfter(JoinPoint joinPoint) {
        outputEndLog(joinPoint);
    }
    private void outputEndLog(JoinPoint joinPoint) {
        String logMessage = "[END] " + getClassName(joinPoint) + "." + getSignatureName(joinPoint);
        logger.info(logMessage);
    }
    /**
     * クラス名の取得
     * @param joinPoint
     * @return
     */
    private String getClassName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getName();
    }

    /**
     * シグネチャ名の取得
     * @param joinPoint
     * @return
     */
    private String getSignatureName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
}
