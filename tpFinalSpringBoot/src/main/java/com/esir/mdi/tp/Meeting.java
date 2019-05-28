package com.esir.mdi.tp;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private String description;

	@OneToMany(mappedBy = "meeting",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Employee> participants;

	private String title;

	private Date dateMeeting;


	public Meeting() {
		super();
	}
	public Meeting(String description, String title, Date date, List<Employee> participants) {
		this.title = title;
		this.description = description;
		this.dateMeeting = date;
		this.participants = participants;
	}
	

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
	
	public void addParticipant(Employee em) {
		this.participants.add(em);
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
