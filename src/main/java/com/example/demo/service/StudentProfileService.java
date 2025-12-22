package com.example.demo.service;

import com.example.demo.model.StudentProfile;
import java.util.List;

public interface StudentProfileService {
    StudentProfile createProfile(StudentProfile profile);
    StudentProfile getProfileById(Long id);
    StudentProfile getProfileByEnrollmentId(String enrollmentId);
    List<StudentProfile> getAllProfiles();
}