package com.example.backend;

import com.example.backend.API.DepartmentController;
import com.example.backend.application.dao.DepartmentDao;
import com.example.backend.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class DepartmentControllerTest {

    @Mock
    private DepartmentDao departmentDao;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAsync() throws ExecutionException, InterruptedException {
        // Mock data
        List<Department> departments = new ArrayList<>();
        var department1 = new Department();
        department1.setName("HR");
        var department2 = new Department();
        department2.setName("IT");
        departments.add(department1);
        departments.add(department2);

        // Mock behavior of departmentDao.getAllDepartments()
        when(departmentDao.getAllDepartments()).thenReturn(CompletableFuture.completedFuture(departments));

        // Call controller method
        CompletableFuture<ResponseEntity<List<Department>>> response = departmentController.getAllAsync();

        // Verify
        assertEquals(ResponseEntity.ok(departments).getBody(), response.get().getBody());
        verify(departmentDao, times(1)).getAllDepartments();
    }

    @Test
    public void testPostAsync_validInput() throws Exception {

        // Mock data
        Department createdDepartment = new Department("IT");

        // Mock behavior of departmentDao.createDepartment()
        when(departmentDao.createDepartment(anyString()))
                .thenReturn(CompletableFuture.completedFuture(createdDepartment));

        // Call controller method
        CompletableFuture<ResponseEntity<Department>> response = departmentController.postAsync(createdDepartment.getName());

        // Verify
        assertEquals(ResponseEntity.created(new URI("/departments/" + createdDepartment.getId())).body(createdDepartment).getBody(),
                response.get().getBody());
        verify(departmentDao, times(1)).createDepartment(eq(createdDepartment.getName()));
    }


    @Test
    public void testPostAsync_invalidInput() throws ExecutionException, InterruptedException {
        String departmentName = "";

        // Call controller method with empty department name
        CompletableFuture<ResponseEntity<Department>> response = departmentController.postAsync(departmentName);

        // Verify
        assertEquals(ResponseEntity.badRequest().body(null), response.get());
        verify(departmentDao, never()).createDepartment(anyString());
    }

    @Test
    public void testGetAsync_existingDepartment() throws ExecutionException, InterruptedException {
        int departmentId = 1;
        Department department = new Department("Engineering");

        // Mock behavior of departmentDao.getDepartmentById()
        when(departmentDao.getDepartmentById(anyInt())).thenReturn(CompletableFuture.completedFuture(department));

        // Call controller method
        CompletableFuture<ResponseEntity<?>> response = departmentController.getAsync(departmentId);

        // Verify
        assertEquals(ResponseEntity.ok(department).getBody(), response.get().getBody());
        verify(departmentDao, times(1)).getDepartmentById(departmentId);
    }

    @Test
    public void testGetAsync_nonExistingDepartment() throws ExecutionException, InterruptedException {
        int nonExistingDepartmentId = 100;

        // Mock behavior of departmentDao.getDepartmentById() returning null
        when(departmentDao.getDepartmentById(anyInt())).thenReturn(CompletableFuture.completedFuture(null));

        // Call controller method
        CompletableFuture<ResponseEntity<?>> response = departmentController.getAsync(nonExistingDepartmentId);

        // Verify
        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("No department found").getBody(), response.get().getBody());
        verify(departmentDao, times(1)).getDepartmentById(nonExistingDepartmentId);
    }

}
