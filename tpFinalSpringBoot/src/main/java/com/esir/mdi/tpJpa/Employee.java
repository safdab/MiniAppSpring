package com.esir.mdi.tpJpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Employee {

	private Long id;
	
	private String name;
	
	private String prenom;
	private String email;
	
	private Departement departement;
	
	public Employee() {
		
	}
	
	public Employee(String name, String prenom, String email, Departement departement) {
		this.setName(name);
		this.setDepartement(departement);
		this.prenom = prenom;
		this.email = email;
	}
	
	public Employee(String name,  String prenom, String email) {
		this.setName(name);
		this.prenom = prenom;
		this.email = email;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	
	@Override
	public String toString() {
		return "Employee [id= " + id + ", name= "+ name + ", departement = "+
					departement.getName()+ "]";
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
