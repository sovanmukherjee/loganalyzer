package com.test.loganalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.loganalyzer.service.LogEventService;

@SpringBootApplication
public class LogAnalyzerApplication implements CommandLineRunner{
	@Autowired
	private LogEventService logEventService;

	public static void main(String[] args) {
		SpringApplication.run(LogAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logEventService.process(logEventService.readFile());
		run();
	}
	
	

}
