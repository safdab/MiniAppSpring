package com.esir.mdi.tp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esir.mdi.tp.models.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
