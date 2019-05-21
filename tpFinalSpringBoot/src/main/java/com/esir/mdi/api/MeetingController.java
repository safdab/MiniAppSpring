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

import com.esir.mdi.tp.Meeting;
import com.esir.mdi.tp.repo.MeetingRepository;

@RestController
public class MeetingController {
	
	
	@Autowired
	private MeetingRepository repository;
	
	//aggregate root
	
	@GetMapping("/meetings")
	List<Meeting> all() {
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
	
	
	@PutMapping("/meetings/{id}")
	Meeting replace(@RequestBody Meeting newMeeting, @PathVariable Long id) {
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

}
