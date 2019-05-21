package com.esir.mdi.api;

public class MeetingNotFoundException extends RuntimeException{
	
	MeetingNotFoundException(Long id) {
		super("Meeting not found with id "+id);
	}

}
