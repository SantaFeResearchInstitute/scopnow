package jp.co.scop.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ヘルスチェック用のコントローラクラス
 */
@RestController
@RequestMapping("/healthCheck")
public class HealthCheckController {

    /**
     * ヘルスチェック用API
     * @return ヘルスチェックOK用レスポンス
     */
    @GetMapping("")
    public ResponseEntity<String> healthCheck() {
         return new ResponseEntity<>("health ok", HttpStatus.OK);
    }
}
