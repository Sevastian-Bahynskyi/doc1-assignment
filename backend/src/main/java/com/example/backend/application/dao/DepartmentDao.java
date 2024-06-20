package com.example.backend.application.dao;

import com.example.backend.model.Department;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DepartmentDao {
    CompletableFuture<Department> createDepartment(String name);
    CompletableFuture<List<Department>> getAllDepartments();
    CompletableFuture<Department> getDepartmentById(int id);
}
