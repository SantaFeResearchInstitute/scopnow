package jp.co.scop.common.controller.advice;

import java.security.AccessControlException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.auth0.jwt.exceptions.JWTVerificationException;

import jp.co.scop.common.exception.BusinessException;
import jp.co.scop.common.exception.ValidationException;

/**
 * アプリケーション全体に関わる例外ハンドリングするためのコントローラアドバイス
 * 
 * ControllerAdviceを指定して、コントローラで発生した例外を一元管理している。
 * ControlleAdviceの参考：
 * 　https://yo1000.gitbooks.io/self-study-spring/content/chapter10.html
 * 
 * SpringMVC(SpringBootの中身)には、個別に例外ハンドリングする方法もあるが、
 * 混在させると動作が保証できないため、ここで一元管理する
 * 　https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#going-deeper
 */
@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
   
	/** メッセージソース */
	@Autowired
	MessageSource messageSource;
	
	/** ロガー */
	private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
	
	/**
	 * バリデーションエラーが発生したときの例外ハンドリング
	 * HTTPステータスを400番でエラーメッセージを返却する
	 * @param e 例外
	 * @return レスポンス
	 */
    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleException(ValidationException e) {
    	// 発生した例外メッセージと、例外発生したフィールド名を取り出す
    	BindingResult result = e.getBindingResult();
    	List<ExceptionEntry> errors = 
    			result.getFieldErrors()
    				.stream()
			    		.map(f->new ExceptionEntry(
			    				f.getField(),
			    				messageSource.getMessage(f.getCode(), f.getArguments(), Locale.JAPAN)
			    			)
			    		)
			    		.collect(Collectors.toList());
    	
    	// エラーレスポンスを作成し400番で返す
    	ExceptionResponse response = new ExceptionResponse();
    	response.setGlobalError(messageSource.getMessage("global.validationError", null, Locale.JAPAN));
    	response.setFieldErrors(errors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

	/**
	 * アプリケーションで想定した業務的な例外ハンドリング
	 * HTTPステータスを400番でエラーメッセージを返却する
	 * @param e 例外
	 * @return レスポンス
	 */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleException(BusinessException e) {

    	logger.error("業務エラーが発生しました。", e);
    	
    	// エラーレスポンスを返す
    	ExceptionResponse response = new ExceptionResponse();
    	response.setGlobalError(messageSource.getMessage(e.getMessage(), null, Locale.JAPAN));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    

    @ExceptionHandler(AccessControlException.class)
    protected ResponseEntity<Object> handleException(AccessControlException e) {

    	logger.error("アクセス権限がありません。", e);
    	
    	// エラーレスポンスを返す
    	ExceptionResponse response = new ExceptionResponse();
    	response.setGlobalError("FORBIDDEN");
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    
    
	/**
	 * 例外ハンドリング
	 * アプリケーションが想定していない（他のハンドリングメソッドで考慮されていない）例外が発生したときのハンドリング
	 * HTTPステータスを500番でエラーメッセージを返却する
	 * @param e 例外
	 * @return レスポンス
	 */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllException(Exception e) {

    	logger.error("想定外エラーが発生しました。", e);

    	/**
    	 * グローバルエラーメッセージにメッセージを設定し、レスポンスを返す
    	 */
    	ExceptionResponse response = new ExceptionResponse();
    	response.setGlobalError(messageSource.getMessage("global.internalServerError", null, Locale.JAPAN));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
