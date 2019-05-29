package com.esir.mdi.tp.api;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.esir.mdi.tp.models.Employee;
import com.esir.mdi.tp.models.Meeting;
import com.esir.mdi.tp.repo.EmployeeRepository;
import com.esir.mdi.tp.repo.MeetingRepository;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api")
public class EmployeeController {
	
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private MeetingRepository meetingRepository;
	
	@GetMapping("/meetings/{meetingId}/employees")
	ResponseEntity<List<Employee>> all(@PathVariable Long meetingId) {
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<Employee> employees = employeeRepository.findAll();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@GetMapping("/meetings/{meetingId}/employees/{employeeId}")
	ResponseEntity<Employee> getOne(@PathVariable Long employeeId, @PathVariable Long meetingId) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

		if(!optionalEmployee.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(optionalEmployee.get(), HttpStatus.OK);

	}
	
	@PostMapping("/meetings/{meetingId}/employees")
	ResponseEntity<Employee> add(@Valid @RequestBody Employee newEmployee, @PathVariable long meetingId) {
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		newEmployee.setMeeting(optionalMeeting.get());
		Employee employee = employeeRepository.save(newEmployee);

		//meeting.addParticipant(employee);

		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}


	@PutMapping("/meetings/{meetingId}/employees/{employeeId}")
	ResponseEntity<Employee> replace(@Valid @RequestBody Employee newEmployee, @PathVariable Long employeeId, @PathVariable Long meetingId) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

		if(!optionalEmployee.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Employee employee = optionalEmployee.get();

		//maj nouveau employé
		if(employee.getUsername()!=null){
			employee.setUsername(newEmployee.getUsername());
		}

		Employee updated = employeeRepository.save(employee);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	
	@DeleteMapping("/meetings/{meetingId}/employees/{employeeId}")
	ResponseEntity<?> delete(@PathVariable Long employeeId, @PathVariable Long meetingId) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

		if(!optionalEmployee.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Employee employee = optionalEmployee.get();
		Meeting meeting = employee.getMeeting();

		meeting.deleteParticipant(employeeId);
		meetingRepository.save(meeting);

		employeeRepository.deleteById(employeeId);


		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	

}
