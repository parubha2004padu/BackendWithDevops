package com.Docker.BackendDevops.repository;

import com.Docker.BackendDevops.entity.StudentDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDataRepository
        extends JpaRepository<StudentDataEntity, Integer> {
}