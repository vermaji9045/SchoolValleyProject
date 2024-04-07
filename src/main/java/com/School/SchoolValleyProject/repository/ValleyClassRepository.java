package com.School.SchoolValleyProject.repository;

import com.School.SchoolValleyProject.Model.ValleyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValleyClassRepository extends JpaRepository<ValleyClass,Integer> {
}
