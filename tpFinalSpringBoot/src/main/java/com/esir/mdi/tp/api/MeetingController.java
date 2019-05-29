package com.esir.mdi.tp.api;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.esir.mdi.tp.models.Employee;
import com.esir.mdi.tp.models.Meeting;
import com.esir.mdi.tp.repo.MeetingRepository;

@RestController
//@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api")
public class MeetingController {
	
	
	@Autowired
	private MeetingRepository repository;
	
		
	@GetMapping("/meetings")
	public ResponseEntity<List<Meeting>> getAllMeetings() {

		List<Meeting> meetings = repository.findAll();
		return new ResponseEntity<>(meetings, HttpStatus.OK);

	}
	
	
	@PostMapping("/meetings")
	public ResponseEntity<Meeting> add(@RequestBody Meeting newMeeting) {
		Meeting meeting = repository.save(newMeeting);
		return new ResponseEntity<>(meeting, HttpStatus.CREATED);
	}
	
	
	//single item 
	
	@GetMapping("/meetings/{id}")
	public ResponseEntity<Meeting> getOne(@PathVariable Long id) {
		Optional<Meeting> optionalMeeting = repository.findById(id);

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(optionalMeeting.get(), HttpStatus.OK);
	}
	
	//Liste les participants à un meeting
	
	@GetMapping("/meetings/{id}/employees")
	public ResponseEntity<List<Employee>> getAEmployeeMeeting(@PathVariable Long id) {
		Optional<Meeting> m = repository.findById(id);
		
		if(!m.isPresent()) {
			throw new MeetingNotFoundException(id);
		}

		return new ResponseEntity<>(m.get().getParticipants(), HttpStatus.OK);
	}
	
	
	@PutMapping("/meetings/{id}")
	public ResponseEntity<Meeting> replace(@RequestBody Meeting newMeeting, @PathVariable Long id) {
		Optional<Meeting> optionalMeeting = repository.findById(id);

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Meeting oldMeeting = optionalMeeting.get();
		//maj nouveau meeting
		if(oldMeeting.getTitle()!=null){
			oldMeeting.setTitle(newMeeting.getTitle());
		}

		if(oldMeeting.getDateMeeting()!=null){
			oldMeeting.setDateMeeting(newMeeting.getDateMeeting());
		}

		if(!oldMeeting.getParticipants().isEmpty()){
			for(Employee participant : newMeeting.getParticipants()){
				oldMeeting.addParticipant(participant);
			}
		}

		Meeting updated = repository.save(oldMeeting);

		return new ResponseEntity<>(updated, HttpStatus.OK);

	}
	
	
	@DeleteMapping("/meetings/{id}")
	public ResponseEntity<Meeting> delete(@PathVariable Long id) {
		Optional<Meeting> optionalMeeting = repository.findById(id);

		if(!optionalMeeting.isPresent()){

		}

		return new ResponseEntity<>(optionalMeeting.get(), HttpStatus.OK);
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
