package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AssessmentResult;
import com.example.demo.repository.AssessmentResultRepository;
import com.example.demo.service.AssessmentService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentResultRepository repository;

    public AssessmentServiceImpl(AssessmentResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public AssessmentResult recordResult(AssessmentResult result) {
        if (result.getScoreObtained() < 0 || result.getScoreObtained() > result.getMaxScore()) {
            throw new IllegalArgumentException("Score must be between 0 and maxScore");
        }
        return repository.save(result);
    }

    @Override
    public List<AssessmentResult> getResultsByStudent(Long studentId) {
        return repository.findByStudentProfileId(studentId);
    }

    @Override
    public AssessmentResult getResultsByStudentAndSkill(Long studentId, Long skillId) {
        return repository.findTopByStudentProfileIdAndSkillIdOrderByAssessedAtDesc(studentId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("AssessmentResult not found"));
    }
}