package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true, nullable = false)
    private String enrollmentId;

    private String cohort;

    private Integer yearLevel;

    private Boolean active = true;

    public StudentProfile() {}

    public StudentProfile(User user, String enrollmentId, String cohort, Integer yearLevel) {
        this.user = user;
        this.enrollmentId = enrollmentId;
        this.cohort = cohort;
        this.yearLevel = yearLevel;
        this.active = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(String enrollmentId) { this.enrollmentId = enrollmentId; }
    public String getCohort() { return cohort; }
    public void setCohort(String cohort) { this.cohort = cohort; }
    public Integer getYearLevel() { return yearLevel; }
    public void setYearLevel(Integer yearLevel) { this.yearLevel = yearLevel; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}