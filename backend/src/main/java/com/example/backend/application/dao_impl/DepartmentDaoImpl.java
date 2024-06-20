package com.example.backend.application.dao_impl;

import com.example.backend.application.dao.DepartmentDao;
import com.example.backend.model.Department;
import com.example.backend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DepartmentDaoImpl implements DepartmentDao {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentDaoImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public CompletableFuture<Department> createDepartment(String name) {
        return CompletableFuture.supplyAsync(() -> {
            Department department = new Department(name);
            return departmentRepository.save(department);
        });
    }

    @Override
    public CompletableFuture<List<Department>> getAllDepartments() {
        return CompletableFuture.supplyAsync(departmentRepository::findAll);
    }

    @Override
    public CompletableFuture<Department> getDepartmentById(int id) {
        return CompletableFuture.supplyAsync(() -> departmentRepository.findById(id).orElse(null));
    }
}
