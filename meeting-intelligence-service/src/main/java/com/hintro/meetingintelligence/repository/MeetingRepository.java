package com.hintro.meetingintelligence.repository;

import com.hintro.meetingintelligence.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
}