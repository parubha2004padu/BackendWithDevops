package com.Docker.BackendDevops.controller;

import com.Docker.BackendDevops.entity.StudentDataEntity;
import com.Docker.BackendDevops.response.ApiResponse;
import com.Docker.BackendDevops.service.StudentDataService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentDataController {

    private final StudentDataService studentDataService;

    public StudentDataController(StudentDataService studentDataService) {
        this.studentDataService = studentDataService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<StudentDataEntity>> insertData(
            @RequestBody StudentDataEntity studentDataEntity) {

        StudentDataEntity student =
                studentDataService.insertData(studentDataEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Student inserted successfully",
                                student
                        )
                );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDataEntity>>> getAllData() {

        List<StudentDataEntity> students =
                studentDataService.getAllData();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Students fetched successfully",
                        students
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDataEntity>> getDataById(
            @PathVariable Integer id) {

        StudentDataEntity student =
                studentDataService.getDataById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Student fetched successfully",
                        student
                )
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDataEntity>> updateData(
            @PathVariable Integer id,
            @RequestBody StudentDataEntity studentDataEntity) {

        StudentDataEntity student =
                studentDataService.updateData(
                        id,
                        studentDataEntity
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Student updated successfully",
                        student
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteData(
            @PathVariable Integer id) {

        studentDataService.deleteData(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Student deleted successfully",
                        null
                )
        );
    }
}