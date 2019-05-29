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
	private MeetingRepository meetingRepository;
	
		
	@GetMapping("/meetings")
	public ResponseEntity<List<Meeting>> getAllMeetings() {

		List<Meeting> meetings = meetingRepository.findAll();
		return new ResponseEntity<>(meetings, HttpStatus.OK);

	}
	
	
	@PostMapping("/meetings")
	public ResponseEntity<Meeting> add(@RequestBody Meeting newMeeting) {
		Meeting meeting = meetingRepository.save(newMeeting);
		return new ResponseEntity<>(meeting, HttpStatus.CREATED);
	}

	
	@GetMapping("/meetings/{meetingId}")
	public ResponseEntity<Meeting> getOne(@PathVariable Long meetingId) {
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(optionalMeeting.get(), HttpStatus.OK);
	}

	
	
	@PutMapping("/meetings/{meetingId}")
	public ResponseEntity<Meeting> replace(@RequestBody Meeting newMeeting, @PathVariable Long meetingId) {
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

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

		Meeting updated = meetingRepository.save(oldMeeting);

		return new ResponseEntity<>(updated, HttpStatus.OK);

	}
	
	
	@DeleteMapping("/meetings/{meetingId}")
	public ResponseEntity<?> delete(@PathVariable Long meetingId) {
		Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

		if(!optionalMeeting.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		meetingRepository.deleteById(meetingId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	


}
