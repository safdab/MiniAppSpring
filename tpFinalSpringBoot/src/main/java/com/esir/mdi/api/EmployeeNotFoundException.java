package com.esir.mdi.api;

public class EmployeeNotFoundException extends RuntimeException{

	EmployeeNotFoundException(Long id) {
		super("Employee not found with id "+id);
	}
}
