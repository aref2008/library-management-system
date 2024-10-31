package com.library.demo.dto.PatronDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class PatronUpdateDTO {
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    private String phone;

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
