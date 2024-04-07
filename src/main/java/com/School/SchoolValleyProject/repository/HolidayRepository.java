package com.School.SchoolValleyProject.repository;

import com.School.SchoolValleyProject.Model.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday,String> {




}
