package com.Docker.BackendDevops.service;

import com.Docker.BackendDevops.entity.StudentDataEntity;
import com.Docker.BackendDevops.exception.StudentNotFoundException;
import com.Docker.BackendDevops.repository.StudentDataRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDataService {

    private final StudentDataRepository studentDataRepository;

    private static final Logger logger =
            LogManager.getLogger(StudentDataService.class);

    public StudentDataService(StudentDataRepository studentDataRepository) {
        this.studentDataRepository = studentDataRepository;
    }

    public StudentDataEntity insertData(
            StudentDataEntity studentDataEntity) {

        logger.info("Inserting student data");

        return studentDataRepository.save(studentDataEntity);
    }

    public List<StudentDataEntity> getAllData() {

        logger.info("Fetching all student data");

        return studentDataRepository.findAll();
    }


    public StudentDataEntity getDataById(Integer id) {

        logger.info("Fetching student data with ID: {}", id);

        return studentDataRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Student not found with ID: {}", id);

                    return new StudentNotFoundException(
                            "Student not found with ID: " + id
                    );
                });
    }


    public StudentDataEntity updateData(
            Integer id,
            StudentDataEntity studentDataEntity) {

        logger.info("Updating student data with ID: {}", id);

        StudentDataEntity existingStudent =
                studentDataRepository.findById(id)
                        .orElseThrow(() -> {
                            logger.error(
                                    "Student not found with ID: {}",
                                    id
                            );

                            return new StudentNotFoundException(
                                    "Student not found with ID: " + id
                            );
                        });

        existingStudent.setName(studentDataEntity.getName());
        existingStudent.setCity(studentDataEntity.getCity());

        StudentDataEntity updatedStudent =
                studentDataRepository.save(existingStudent);

        logger.info(
                "Student updated successfully with ID: {}",
                id
        );

        return updatedStudent;
    }


    public void deleteData(Integer id) {

        logger.info("Deleting student data with ID: {}", id);

        StudentDataEntity existingStudent =
                studentDataRepository.findById(id)
                        .orElseThrow(() -> {
                            logger.error(
                                    "Student not found with ID: {}",
                                    id
                            );

                            return new StudentNotFoundException(
                                    "Student not found with ID: " + id
                            );
                        });

        studentDataRepository.delete(existingStudent);

        logger.info(
                "Student deleted successfully with ID: {}",
                id
        );
    }
}