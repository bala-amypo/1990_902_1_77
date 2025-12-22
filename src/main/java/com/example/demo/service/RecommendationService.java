package com.example.demo.service;

import com.example.demo.model.SkillGapRecommendation;
import java.util.List;

public interface RecommendationService {
    List<SkillGapRecommendation> generateRecommendations(Long studentProfileId);
    List<SkillGapRecommendation> getRecommendationsByStudent(Long studentId);
}