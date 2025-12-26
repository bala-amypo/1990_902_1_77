package com.example.demo.service.impl;

import com.example.demo.model.AssessmentResult;
import com.example.demo.model.Skill;
import com.example.demo.model.SkillGapRecord;
import com.example.demo.repository.AssessmentResultRepository;
import com.example.demo.repository.SkillGapRecordRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.service.SkillGapService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SkillGapServiceImpl implements SkillGapService {

    private final SkillGapRecordRepository skillGapRecordRepository;
    private final SkillRepository skillRepository;
    private final AssessmentResultRepository assessmentResultRepository;

    public SkillGapServiceImpl(SkillGapRecordRepository skillGapRecordRepository, SkillRepository skillRepository, AssessmentResultRepository assessmentResultRepository) {
        this.skillGapRecordRepository = skillGapRecordRepository;
        this.skillRepository = skillRepository;
        this.assessmentResultRepository = assessmentResultRepository;
    }

    @Override
    public List<SkillGapRecord> computeGaps(Long studentProfileId) {
        List<Skill> activeSkills = skillRepository.findByActiveTrue();
        List<SkillGapRecord> gaps = new ArrayList<>();

        for (Skill skill : activeSkills) {
            Optional<AssessmentResult> latestResult = assessmentResultRepository
                    .findTopByStudentProfileIdAndSkillIdOrderByAssessedAtDesc(studentProfileId, skill.getId());
            
            if (latestResult.isPresent()) {
                AssessmentResult result = latestResult.get();
                double currentScore = (result.getScoreObtained() / result.getMaxScore()) * 100;
                double targetScore = skill.getMinCompetencyScore();
                double gapScore = Math.max(0, targetScore - currentScore);

                SkillGapRecord gap = new SkillGapRecord(
                        result.getStudentProfile(),
                        skill,
                        currentScore,
                        targetScore,
                        gapScore
                );
                gaps.add(skillGapRecordRepository.save(gap));
            }
        }
        return gaps;
    }

    @Override
    public List<SkillGapRecord> getGapsByStudent(Long studentId) {
        return skillGapRecordRepository.findByStudentProfileId(studentId);
    }
}