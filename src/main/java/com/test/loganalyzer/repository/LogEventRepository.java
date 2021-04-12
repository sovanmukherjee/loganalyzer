package com.test.loganalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.loganalyzer.model.LogEvent;

@Repository
public interface LogEventRepository extends JpaRepository<LogEvent, String> {
	
	

}
