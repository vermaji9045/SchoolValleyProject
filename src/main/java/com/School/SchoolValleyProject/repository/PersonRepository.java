package com.School.SchoolValleyProject.repository;

import com.School.SchoolValleyProject.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    Person readByEmail(String email);
}
