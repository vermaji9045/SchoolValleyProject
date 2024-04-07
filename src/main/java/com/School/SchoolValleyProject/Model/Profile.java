package com.School.SchoolValleyProject.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Profile {

    @NotBlank(message = "Name must not be blank")
    @Size(min=3,message = "Name must be at east 3 characters long")
    private String name;


    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email must not be blank")
    @Email(message="Please provide a valid email address")
    private String email;


    @NotBlank(message = "Address must not be blank")
    @Size(min=5,message = "Address must be at least 5 characters long")

    private String address1;
    private String address2;

    @NotBlank(message = "City must not be blank")
    @Size(min=5,message = "City must be at east 5 characters long")
    private String city;

    @NotBlank(message = "state must not be blank")
    @Size(min=5,message = "State must be at east 5 characters long")
    private String state;

    @NotBlank(message = "Zip code must not be blank")
    @Pattern(regexp="(^$|[0-9]{5})",message = "Zip code must be 5 digits")
    private String zipCode;


}
