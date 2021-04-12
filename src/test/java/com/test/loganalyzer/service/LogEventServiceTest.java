package com.test.loganalyzer.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.test.loganalyzer.dto.LogEventDTO;
import com.test.loganalyzer.model.LogEvent;
import com.test.loganalyzer.repository.LogEventRepository;

@RunWith(MockitoJUnitRunner.class)
public class LogEventServiceTest {

	@Mock
    private LogEventRepository logEventRepositoryMock;
	
	@InjectMocks
	private  LogEventService logEventServiceMock;
	
	@Test
	public  void processTest() throws IOException{
		List<LogEvent> logEvents = new ArrayList<>();
		List<LogEventDTO> logEventDto = new ArrayList<>();
		LogEventDTO dto = new LogEventDTO();
		dto.setId("11");
		dto.setState("STARTED");
		dto.setTimestamp(1491377495212L);
		logEventDto.add(dto);
		
		LogEventDTO dto1 = new LogEventDTO();
		dto1.setId("11");
		dto1.setState("FINISHED");
		dto1.setTimestamp(1491377495217L);
		logEventDto.add(dto1);
		
		
		Mockito.when(logEventRepositoryMock.saveAll(Mockito.any())).thenReturn(logEvents);
		System.out.println(logEventServiceMock);
		List<LogEvent> evnts = logEventServiceMock.process(logEventDto); 
		assertNotNull(evnts);
	}

}
