package com.School.SchoolValleyProject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Address extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int addressId;

    @NotBlank(message = "Address must not be Blank")
    @Size(min = 10,message = "Address must be 5 characters")
    private String address1;
    private String address2;

    @NotBlank(message = "City must not be Blank")
    @Size(min = 5,message = "City must be 5 characters")
    private String city;

    @NotBlank(message = "State must not be Blank")
    @Size(min = 5,message = "State must be 5 characters")
    private String state;

    @NotBlank(message = "ZipCode must not be Blank")

    private String zipCode;



}
