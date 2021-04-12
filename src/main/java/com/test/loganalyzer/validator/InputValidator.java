package com.test.loganalyzer.validator;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.test.loganalyzer.consts.State;
import com.test.loganalyzer.dto.LogEventDTO;
import com.test.loganalyzer.exception.LogAnalyzerException;

@Component
public class InputValidator {

	public static void isValid(final List<LogEventDTO> logEvents) {
		
		logEvents.forEach(k->{
			if(null == k.getId() || null == k.getState() || isValidState().test(k.getState())) {
				throw new LogAnalyzerException("id, state, timestamp is not valid.");
			}
		});
	}
	
	
	static Predicate<String>  isValidState(){
		return  x-> !State.STARTED.toString().equalsIgnoreCase(x) && !State.FINISHED.toString().equalsIgnoreCase(x);
	}


	public static void isValidEvent(Entry<String, List<LogEventDTO>> event) {
		if(event.getValue().size()>2) {
			throw new LogAnalyzerException(event.getKey() +" event id has more than 2 state.");
		}
		
	}
}
