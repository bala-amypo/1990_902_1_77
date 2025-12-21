package com.example.demo.controller;

import com.example.demo.model.StudentProfile;
import com.example.demo.service.StudentProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Profiles")
public class StudentProfileController {

    private final StudentProfileService service;

    public StudentProfileController(StudentProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StudentProfile> createProfile(@RequestBody StudentProfile profile) {
        return ResponseEntity.ok(service.createProfile(profile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentProfile> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProfileById(id));
    }

    @GetMapping("/enrollment/{id}")
    public ResponseEntity<StudentProfile> getProfileByEnrollmentId(@PathVariable String id) {
        return ResponseEntity.ok(service.getProfileByEnrollmentId(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentProfile>> getAllProfiles() {
        return ResponseEntity.ok(service.getAllProfiles());
    }
}