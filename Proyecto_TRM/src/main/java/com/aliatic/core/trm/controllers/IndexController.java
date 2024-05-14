package com.aliatic.core.trm.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aliatic.core.trm.config.AliaticLogger;
import static com.aliatic.core.trm.config.Textos.Es.*;

@RestController
public class IndexController {

	@Value("${trm.app.version}")
	private String versionNumber;

	@GetMapping("/")
	public ResponseEntity<String> index() {

		try {
			return ResponseEntity.ok(VERSION.getVal().concat(versionNumber));
		}catch (Exception e) {
			AliaticLogger.error(SE_HA_PRESENTADO_UN_ERROR.getVal(), this.getClass().getName(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), e.getMessage());

			return ResponseEntity.internalServerError().body(SE_HA_PRESENTADO_UN_ERROR.getVal());
		}
	}

}
