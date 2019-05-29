package com.esir.mdi.tp.api;

public class MeetingNotFoundException extends RuntimeException{
	
	MeetingNotFoundException(Long id) {
		super("Meeting not found with id "+id);
	}

}
