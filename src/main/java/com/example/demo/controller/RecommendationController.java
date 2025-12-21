package com.example.demo.controller;

import com.example.demo.model.SkillGapRecommendation;
import com.example.demo.service.RecommendationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@Tag(name = "Recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @PostMapping("/generate/{studentId}")
    public ResponseEntity<List<SkillGapRecommendation>> generateRecommendations(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.generateRecommendations(studentId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<SkillGapRecommendation>> getRecommendations(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getRecommendationsByStudent(studentId));
    }
}