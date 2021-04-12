package com.test.loganalyzer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.loganalyzer.model.LogEvent;
import com.test.loganalyzer.service.LogEventService;

@RestController
public class LogAnalyzerController {

	@Autowired
	private LogEventService logEventService;
	
	
	@GetMapping
	public ResponseEntity<List<LogEvent>> get(){
		return ResponseEntity.ok(logEventService.get());
	}
	
}
