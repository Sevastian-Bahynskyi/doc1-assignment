package com.example.backend.API;

import com.example.backend.application.dao.DepartmentDao;
import com.example.backend.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentController(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Department>>> getAllAsync() {
        System.out.println("I got here!");
        return departmentDao.getAllDepartments()
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.status(500).body(null));
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Department>> postAsync(@RequestBody String name) {
        if (name == null || name.trim().isEmpty()) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body(null));
        }

        return departmentDao.createDepartment(name)
                .thenApply(createdDepartment -> ResponseEntity.created(
                        URI.create("/departments/" + createdDepartment.getId())).body(createdDepartment))
                .exceptionally(ex -> ResponseEntity.status(500).body(null));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> getAsync(@PathVariable int id) {
        return departmentDao.getDepartmentById(id)
                .thenApply(department -> {
                    if (department != null) {
                        return ResponseEntity.ok(department);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No department found");
                    }
                })
                .exceptionally(ex -> ResponseEntity.status(500).body(null));
    }
}
