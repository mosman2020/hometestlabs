package com.hometest.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SignupDto {
    private String firstName;
    private String middleName;
    private String familyName;
    private String title;
    private String gender;
    private Date birthDate;
    private String mobile;
    private String userName;
    private String password;
    private String preferedLanguage;

}
