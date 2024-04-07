package com.School.SchoolValleyProject.Services;

import com.School.SchoolValleyProject.Constants.ValleyPublicConst;
import com.School.SchoolValleyProject.Model.Person;
import com.School.SchoolValleyProject.Model.Roles;
import com.School.SchoolValleyProject.repository.PersonRepository;
import com.School.SchoolValleyProject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean CreateNewUser(Person person) {
        boolean issave = false;

        Roles roles = roleRepository.getByRoleName(ValleyPublicConst.STUDENT_ROLE);

        person.setRoles(roles);

        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if (null != person && person.getPerson_id() > 0) {
            issave = true;
        }
        return issave;

    }
}
