package com.School.SchoolValleyProject.repository;

import com.School.SchoolValleyProject.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Integer> {

    Roles getByRoleName(String roleName);
}
