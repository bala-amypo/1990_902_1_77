package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Skill;
import com.example.demo.repository.SkillRepository;
import com.example.demo.service.SkillService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository repository;

    public SkillServiceImpl(SkillRepository repository) {
        this.repository = repository;
    }

    @Override
    public Skill createSkill(Skill skill) {
        if (skill.getMinCompetencyScore() < 0 || skill.getMinCompetencyScore() > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        return repository.save(skill);
    }

    @Override
    public Skill updateSkill(Long id, Skill skill) {
        Skill existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        if (skill.getMinCompetencyScore() < 0 || skill.getMinCompetencyScore() > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        existing.setSkillName(skill.getSkillName());
        existing.setCategory(skill.getCategory());
        existing.setDescription(skill.getDescription());
        existing.setMinCompetencyScore(skill.getMinCompetencyScore());
        return repository.save(existing);
    }

    @Override
    public Skill getSkillById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
    }

    @Override
    public List<Skill> getAllSkills() {
        return repository.findAll();
    }

    @Override
    public void deactivateSkill(Long id) {
        Skill skill = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        skill.setActive(false);
        repository.save(skill);
    }
}