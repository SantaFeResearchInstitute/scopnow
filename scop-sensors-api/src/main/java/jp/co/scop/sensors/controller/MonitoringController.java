package jp.co.scop.sensors.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;

import jp.co.scop.sensors.service.FailureSensorGroupService;
import jp.co.scop.sensors.service.NotificationService;

/**
 * 監視用コントローラー
 * @author ncxxsl-koseki
 *
 */
@RestController
@RequestMapping("${system.url}/monitoring")
public abstract class MonitoringController {

}
