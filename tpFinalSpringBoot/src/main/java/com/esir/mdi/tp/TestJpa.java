package com.esir.mdi.tp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.esir.mdi.tp.Employee;

public class TestJpa {

	private EntityManager manager;
	
	public TestJpa(EntityManager manager) {
		this.manager = manager;
	}
	
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("example");
		EntityManager manager = factory.createEntityManager();
		TestJpa test = new TestJpa(manager);
		
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		
		try {
			test.createEmployees();
		} catch(Exception e) {
			e.printStackTrace();
		}
		tx.commit();
		test.listEmployees();
		
		manager.close();
		System.out.println("...done");
	}
	
	public void createEmployees() {
		int numofEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
		if(numofEmployees == 0) {
			manager.persist(new Employee("jakab", "Gipsz","moi@moi.com"));
			manager.persist(new Employee("Captain", "Nemo","nemo@moi.com"));

		}
	}
	
	private void listEmployees() {
		List<Employee> resultList = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
		System.out.println("number of employees : " + resultList.size());
		for(Employee next : resultList) {
			System.out.println("next employee : "+ next);
		}
	}
	
	
	
	
	
	
	
	
}
