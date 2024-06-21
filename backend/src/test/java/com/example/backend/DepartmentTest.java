package com.example.backend;

import com.example.backend.model.Department;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DepartmentTest {

    @Test
    public void testDepartmentConstructor() {
        // Given
        String departmentName = "Engineering";

        // When
        Department department = new Department(departmentName);

        // Then
        assertNotNull(department);
        assertEquals(departmentName, department.getName());
    }

    @Test
    public void testDefaultConstructor() {
        // Given and When
        Department department = new Department();

        // Then
        assertNotNull(department);
        // Since the default constructor does not set the name, it should be null
        assertEquals(null, department.getName());
    }

    @Test
    public void testSetName() {
        // Given
        Department department = new Department();
        String departmentName = "HR";

        // When
        department.setName(departmentName);

        // Then
        assertEquals(departmentName, department.getName());
    }

    @Test
    public void testGetId() {
        Department department = new Department("Finance");

        int id = department.getId();

        assertEquals(0, id);
    }
}
