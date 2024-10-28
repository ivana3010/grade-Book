package com.iktpreobuka.elektronski_dnevnik.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronski_dnevnik.controllers.util.RESTError;

@RestController
@RequestMapping("/log")
public class LoggingController {
	//preuzimanje logera
	private static final String LOG_FILE_PATH = "logs/spring-boot-logging.log";//konstanta koja sadrzi putanju do fajla

	@GetMapping
	public ResponseEntity<?> getLogFile(@RequestParam String role) {
		if (!"admin".equalsIgnoreCase(role)) {
			return new ResponseEntity<>(new RESTError(1, "Unauthorized access"), HttpStatus.UNAUTHORIZED);
		}

		File logFile = new File(LOG_FILE_PATH);
		if (!logFile.exists()) {
			return new ResponseEntity<>(new RESTError(2, "Log file not found"), HttpStatus.NOT_FOUND);
		}

		try {
			InputStreamResource resource = new InputStreamResource(new FileInputStream(logFile));//objekat koji cita log file
			HttpHeaders headers = new HttpHeaders();//klasa koja predstavlja http zaglavlje
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + logFile.getName());//content disposition se koristi da kazemo da je u pitanju prilog i stavljamo naziv fajla za preuzimanje
			headers.add(HttpHeaders.CONTENT_TYPE, "text/plain");//tip tog sadrzaja
			return ResponseEntity.ok().headers(headers).contentLength(logFile.length()).body(resource);//kreira http odgovor, telo sadzri onaj procitani fajl iz inputstream
		} catch (FileNotFoundException e) {
			return new ResponseEntity<>(new RESTError(3, "Error reading log file"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
