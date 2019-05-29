package com.esir.mdi.tp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="username")
	private String username;

	
	@ManyToOne
	@JsonIgnore
	private Meeting meeting;
	
	public Employee() {

	}
	
	public Employee(String username) {
		this.username = username;

	}
	

	public Long getId() {

		return id;
	}
	
	public void setId(Long id) {

		this.id = id;
	}

	public String getUsername(){

		return this.username;
	}

	public void setUsername(String username){

		this.username = username;
	}

	public Meeting getMeeting() {

		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
}
