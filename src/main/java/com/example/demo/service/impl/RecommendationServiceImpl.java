package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.AssessmentResult;
import com.example.demo.model.Skill;
import com.example.demo.model.SkillGapRecommendation;
import com.example.demo.model.StudentProfile;
import com.example.demo.repository.AssessmentResultRepository;
import com.example.demo.repository.SkillGapRecommendationRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.repository.StudentProfileRepository;
import com.example.demo.service.RecommendationService;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final AssessmentResultRepository assessmentRepository;
    private final SkillGapRecommendationRepository recommendationRepository;
    private final StudentProfileRepository studentRepository;
    private final SkillRepository skillRepository;

    public RecommendationServiceImpl(AssessmentResultRepository assessmentRepository, 
                                   SkillGapRecommendationRepository recommendationRepository,
                                   StudentProfileRepository studentRepository,
                                   SkillRepository skillRepository) {
        this.assessmentRepository = assessmentRepository;
        this.recommendationRepository = recommendationRepository;
        this.studentRepository = studentRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public List<SkillGapRecommendation> generateRecommendations(Long studentProfileId) {
        StudentProfile student = studentRepository.findById(studentProfileId).orElse(null);
        if (student == null) return new ArrayList<>();

        List<Skill> activeSkills = skillRepository.findByActiveTrue();
        List<SkillGapRecommendation> recommendations = new ArrayList<>();

        for (Skill skill : activeSkills) {
            Optional<AssessmentResult> latestResult = assessmentRepository
                    .findTopByStudentProfileIdAndSkillIdOrderByAssessedAtDesc(studentProfileId, skill.getId());
            
            if (latestResult.isPresent()) {
                AssessmentResult result = latestResult.get();
                double currentScore = (result.getScoreObtained() / result.getMaxScore()) * 100;
                double gapScore = Math.max(0, skill.getMinCompetencyScore() - currentScore);

                if (gapScore > 0) {
                    String priority = gapScore > 20 ? "HIGH" : gapScore > 10 ? "MEDIUM" : "LOW";
                    String action = "Focus on improving " + skill.getSkillName() + " skills";

                    SkillGapRecommendation recommendation = new SkillGapRecommendation(
                            student, skill, action, priority, gapScore, "SYSTEM"
                    );
                    recommendations.add(recommendationRepository.save(recommendation));
                }
            }
        }
        return recommendations;
    }

    @Override
    public List<SkillGapRecommendation> getRecommendationsByStudent(Long studentId) {
        return recommendationRepository.findByStudentProfileIdOrderByGeneratedAtDesc(studentId);
    }
}