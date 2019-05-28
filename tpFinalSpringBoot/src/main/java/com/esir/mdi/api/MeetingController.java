package com.esir.mdi.api;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esir.mdi.tp.Employee;
import com.esir.mdi.tp.Meeting;
import com.esir.mdi.tp.repo.MeetingRepository;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/polls")
public class MeetingController {
	
	
	@Autowired
	private MeetingRepository repository;
	
	//aggregate root
	
	@GetMapping("/meetings")
	List<Meeting> getAllMeetings() {
		return repository.findAll();
	}
	
	
	@PostMapping("/meetings")
	Meeting add(@RequestBody Meeting newMeeting) {
		return repository.save(newMeeting);
	}
	
	
	//single item 
	
	@GetMapping("/meetings/{id}")
	Meeting getOne(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new MeetingNotFoundException(id));
	}
	
	//Liste les participants à un meeting
	
	@GetMapping("/meetings/{id}/employees")
	ResponseEntity<List<Employee>> getAEmployeeMeeting(@PathVariable Long id) {
		Optional<Meeting> m = repository.findById(id);
		
		if(!m.isPresent()) {
			throw new MeetingNotFoundException(id);
		}

		return new ResponseEntity<>(m.get().getParticipants(), HttpStatus.OK);
	}
	
	
	@PutMapping("/meetings/{id}")
	public Meeting replace(@RequestBody Meeting newMeeting, @PathVariable Long id) {
		return repository.findById(id)
				.map(meeting -> {
					meeting.setDateMeeting(newMeeting.getDateMeeting());
					meeting.setDescription(newMeeting.getDescription());
					meeting.setParticipants(newMeeting.getParticipants());
					meeting.setTitle(meeting.getTitle());
					return repository.save(meeting);
				})
				.orElseGet(() -> {
					newMeeting.setId(id);
					return repository.save(newMeeting);
				});
	}
	
	
	@DeleteMapping("/meetings/{id}")
	void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	/**
	 * Ajoute un employee dans un meeting
	 * @param id
	 * @param employee
	 * @return
	 */
	@PostMapping("/meetings/{idMeeting}/employees")
	public ResponseEntity<Meeting> addEmployee(@PathParam("idMeeting") Long id, @Valid Employee employee) {
		Optional<Meeting> m = repository.findById(id);
		
		if(!m.isPresent()) {
			throw new MeetingNotFoundException(id);
		}
		Meeting m1 = m.get();
		m1.addParticipant(employee);
		repository.save(m1);
		
		return new ResponseEntity<>(m1, HttpStatus.OK);
	}

}
