package com.esir.mdi.tp.api;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.esir.mdi.tp.models.Employee;
import com.esir.mdi.tp.models.Meeting;
import com.esir.mdi.tp.repo.EmployeeRepository;
import com.esir.mdi.tp.repo.MeetingRepository;

import javax.validation.Valid;

@RestController
//@CrossOrigin(origins = "http://localhost:8080")
//@RequestMapping("/api")
public class EmployeeController {
	
	
	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private MeetingRepository meetingRepository;
	
	@GetMapping("/meetings/{meetingId}/employees")
	ResponseEntity<List<Employee>> all(@PathVariable Long meetingId) {
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);

		if(!meeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<Employee> employees = repository.findAll();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@GetMapping("/meetings/{meetingId}/employees/{id}")
	ResponseEntity<Employee> getOne(@PathVariable Long id, @PathVariable Long meetingId) {
		Optional<Employee> employee = repository.findById(id);
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);

		if(!employee.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(!meeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(employee.get(), HttpStatus.OK);

	}
	
	@PostMapping("/meetings/{meetingId}/employees")
	ResponseEntity<Employee> add(@Valid @RequestBody Employee newEmployee, @PathVariable Long meetingId) {
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Meeting meeting = optionalMeeting.get();
		newEmployee.setMeeting(meeting);

		Employee employee = repository.save(newEmployee);

		//meeting.addParticipant(employee);

		return new ResponseEntity<>(employee, HttpStatus.CREATED);
	}


	@PutMapping("/meetings/{meetingId}/employees/{id}")
	ResponseEntity<Employee> replace(@Valid @RequestBody Employee newEmployee, @PathVariable Long id, @PathVariable Long meetingId) {
		Optional<Employee> employee = repository.findById(id);
		Optional<Meeting> meeting = meetingRepository.findById(meetingId);

		if(!employee.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(!meeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Employee oldEmployee = employee.get();
		if(oldEmployee.getUsername()!=null){
			oldEmployee.setUsername(newEmployee.getUsername());
		}
		if(oldEmployee.getMeeting()!=null){
			oldEmployee.setMeeting(meeting.get());
		}
		Employee updated = repository.save(oldEmployee);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	
	@DeleteMapping("/meetings/{meetingId}/employees/{id}")
	ResponseEntity<Employee> delete(@PathVariable Long id, @PathVariable Long meetingId) {
		Optional<Employee> optionalEmployee = repository.findById(id);
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

		if(!optionalEmployee.isPresent() || !optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Employee employee = optionalEmployee.get();
		Meeting meeting = employee.getMeeting();
		meeting.deleteParticipant(id);
		repository.deleteById(id);

		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	
	

}
