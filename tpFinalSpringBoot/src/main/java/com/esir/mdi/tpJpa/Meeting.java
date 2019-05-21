package com.esir.mdi.tpJpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Meeting {

	private Long Id;
	
	private String description;
	
	private List<Employee> participants = new ArrayList<Employee>();
	
	private String title;
	
	private Date dateMeeting;

	public Meeting() {
		
	}
	
	public Meeting(String desc, String title, Date dateMeeting) {
		this.dateMeeting = dateMeeting;
		this.title = title;
		this.description = desc;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Employee> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Employee> participants) {
		this.participants = participants;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDateMeeting() {
		return dateMeeting;
	}

	public void setDateMeeting(Date dateMeeting) {
		this.dateMeeting = dateMeeting;
	}
}
