package com.example.securitydemo.service;

import com.example.securitydemo.dto.DeveloperDTO;

public interface DeveloperService {

    void saveDeveloper();

    DeveloperDTO updateDeveloper();

    void deleteDeveloper();

    DeveloperDTO getDeveloper(int id);

}
