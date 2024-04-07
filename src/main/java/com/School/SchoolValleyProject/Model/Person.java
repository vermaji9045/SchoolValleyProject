package com.School.SchoolValleyProject.Model;

import com.School.SchoolValleyProject.annotations.FieldsValueMatch;
import com.School.SchoolValleyProject.annotations.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@FieldsValueMatch.List({

        @FieldsValueMatch(

                field = "pwd",
                fieldMatch = "confirmPwd",
                message="Password do not match"
        ),
        @FieldsValueMatch(

                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email do not match"
        )
})
public class Person extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int person_id;

    @NotBlank(message = "Name must not be Blank")
    @Size(min=3,message = "Name must have at least 5 characters")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message="Please provide a valid mail id")
    private String email;
    @NotBlank(message = "Confirm email not be blank")
    @Email(message = "Please provide confirm email address")
    @Transient
    private String confirmEmail;

    @NotBlank(message = "Password must not be blanl")
    @Size(min=5,message = "Password must be at least 5 char long")
    private String pwd;

    @NotBlank(message = "Confirm Password must not blank ")
    @Size(min = 5,message = "Confirm Password must be at least 5 char long")
    @Transient
    @PasswordValidator
    private String confirmPwd;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Address.class)
    @JoinColumn(name="address_id" ,referencedColumnName="addressId",nullable = true)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id",nullable = false)
    private Roles roles;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "class_id", referencedColumnName = "classId", nullable = true)
    private ValleyClass valleyClass;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)

    @JoinTable(name = "person_courses",
            joinColumns ={

            @JoinColumn(name="person_id", referencedColumnName="person_id")
            },
            inverseJoinColumns = {
            @JoinColumn(name="course_id",referencedColumnName="courseId")
            })
    private Set<courses> courses=new HashSet<>();

}
