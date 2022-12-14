package com.example.securitydemo.service.impl;

import com.example.securitydemo.dto.DeveloperDTO;
import com.example.securitydemo.service.DeveloperService;
import org.springframework.stereotype.Service;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Override
    public void saveDeveloper() {
        throw new UnsupportedOperationException("Have not implement [saveDeveloper] function");
    }

    @Override
    public DeveloperDTO updateDeveloper() {
        throw new UnsupportedOperationException("Have not implement [updateDeveloper] function");
    }

    @Override
    public void deleteDeveloper() {
        throw new UnsupportedOperationException("Have not implement [deleteDeveloper] function");
    }

    @Override
    public DeveloperDTO getDeveloper(int id) {
        throw new UnsupportedOperationException("Have not implement [getDeveloper] function");
    }
}
