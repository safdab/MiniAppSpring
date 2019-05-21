package com.esir.mdi.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.esir.mdi.tp.Employee;
import com.esir.mdi.tp.repo.EmployeeRepository;

@RestController
public class EmployeeController {
	
	
	@Autowired
	private EmployeeRepository repository;
	
	//aggregate root
	
	@GetMapping("/employees")
	List<Employee> all() {
		return repository.findAll();
	}
	
	@PostMapping("/employees")
	Employee add(@RequestBody Employee newEmployee) {
		return repository.save(newEmployee);
	}
	
	//single item
	
	@GetMapping("/employees/{id}")
	Employee getOne(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
	}
	
	@PutMapping("/employees/{id}")
	Employee replace(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return repository.findById(id)
				.map(employee -> {
					employee.setName(newEmployee.getName());
					employee.setPrenom(newEmployee.getPrenom());
					employee.setEmail(newEmployee.getEmail());
					return repository.save(employee);
				})
				.orElseGet(() -> {
					newEmployee.setId(id);
					return repository.save(newEmployee);
				});
	}
	
	@DeleteMapping("/employees/{id}")
	void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	
	

}
