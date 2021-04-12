package com.test.loganalyzer.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.loganalyzer.consts.State;
import com.test.loganalyzer.dto.LogEventDTO;
import com.test.loganalyzer.model.LogEvent;
import com.test.loganalyzer.repository.LogEventRepository;
import com.test.loganalyzer.validator.InputValidator;


@Service	
public class LogEventService {
	
	Logger log = Logger.getLogger("LogEventService.class");
	@Autowired
	private LogEventRepository logEventRepository;
	
	private static <T> T parse(String jsonData, Class<T> clazz) {
		T result = null;
		try {
			result = new ObjectMapper().readValue(jsonData, clazz);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static long getTimestamp(List<LogEventDTO> events, String state) {
		return events.stream()
		.filter(k->state.equalsIgnoreCase(k.getState()))
		.map(LogEventDTO::getTimestamp)
		.findAny()
		.get();
	}
	
	private Long getDurationForEvent(List<LogEventDTO> events) {
		Long finishedTimeStamp = getTimestamp(events,State.FINISHED.toString());
		Long startTimeStamp = getTimestamp(events,State.STARTED.toString());
		if(null != finishedTimeStamp && null!= startTimeStamp ) {
			return finishedTimeStamp-startTimeStamp;
		}else {
			return null;
		}
		
	}
	
	private LogEvent getLogEvent(Long duration,List<LogEventDTO> events) {
		LogEventDTO logEventDTO = events.get(0);
		LogEvent logEvent = new LogEvent();
		logEvent.setDuration(duration);
		logEvent.setEventType(logEventDTO.getType());
		logEvent.setEventId(logEventDTO.getId());
		logEvent.setHost(logEventDTO.getHost());
		logEvent.setAlert(duration>4?true:false);
		return logEvent;
	}
	
	public List<LogEventDTO> readFile() throws IOException {
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        log.info("Enter file path: ");
	        String path = br.readLine();
	        File file = new File(path);
	        if (file.exists()){
	        	 try (Stream<String> stream = Files.lines(Paths.get(path))) {
	        		return  stream
	        		.filter(k-> k!= null && k.trim().length()>0)
	        		.map(k->{
	        			 return parse(k,LogEventDTO.class);
	        		 }).collect(Collectors.toList());
	        	 }
	        }else {
	        	log.info("Please enter correct file path.");
	        	readFile();
	        }
		return null;
	}

	public List<LogEvent> process(final List<LogEventDTO> logEvents) {
		InputValidator.isValid(logEvents);
		Map<String, List<LogEventDTO>> events = logEvents.stream().collect(Collectors.groupingBy(LogEventDTO::getId));
		List<LogEvent> eventsEntity = new ArrayList<>();
		for(Map.Entry<String, List<LogEventDTO>> event : events.entrySet() ) {
			InputValidator.isValidEvent(event);
			Long duration  = getDurationForEvent(event.getValue());
			eventsEntity.add(getLogEvent(duration,event.getValue()));
		}
		return logEventRepository.saveAll(eventsEntity);
	}

	public List<LogEvent>  get() {
		return logEventRepository.findAll();
		
	}
	
	

}
