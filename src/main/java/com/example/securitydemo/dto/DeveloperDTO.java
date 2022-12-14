package com.example.securitydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DeveloperDTO {

    private int id;

    private String firstName;

    private String lastName;

    private int age;

}
