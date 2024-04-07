package com.School.SchoolValleyProject.repository;

import com.School.SchoolValleyProject.Model.courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
public interface CoursesRepository extends JpaRepository<courses, Integer> {

    List<courses> findByOrderByNameDesc();
    List<courses> findByOrderByName();

}
