package com.example.demo.service;

import com.example.demo.model.AssessmentResult;
import java.util.List;

public interface AssessmentService {
    AssessmentResult recordResult(AssessmentResult result);
    List<AssessmentResult> getResultsByStudent(Long studentId);
    AssessmentResult getResultsByStudentAndSkill(Long studentId, Long skillId);
}