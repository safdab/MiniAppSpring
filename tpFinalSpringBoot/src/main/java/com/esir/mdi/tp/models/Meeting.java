package com.esir.mdi.tp.models;

import com.esir.mdi.tp.models.Employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@OneToMany(mappedBy = "meeting",cascade = CascadeType.ALL)
	private List<Employee> participants = new ArrayList<>();

	@Column(name="title")
	private String title;

	@Column(name="dateMeeting")
	@Temporal(TemporalType.DATE)
	private Date dateMeeting;


	public Meeting() {

	}
	public Meeting(String title, Date date, List<Employee> participants) {
		this.title = title;
		this.dateMeeting = date;
		this.participants = participants;
	}
	

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public void deleteParticipant(Long id) {
		this.participants.remove(id);
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
