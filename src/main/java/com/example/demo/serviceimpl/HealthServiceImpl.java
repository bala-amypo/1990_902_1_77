package com.example.demo.serviceimpl;

import com.example.demo.service.HealthService;
import org.springframework.stereotype.Service;

@Service
public class HealthServiceImpl implements HealthService {
    
    @Override
    public String checkHealth() {
        return "OK";
    }
}