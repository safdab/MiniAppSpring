package com.esir.mdi.tp.api;

public class EmployeeNotFoundException extends RuntimeException{

	EmployeeNotFoundException(Long id) {
		super("Employee not found with id "+id);
	}
}
