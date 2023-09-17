package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllEmployee() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John", "Male", null, "123 Main St"));
        employees.add(new Employee(2L, "Jane", "Female", null, "456 Elm St"));

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.findAllEmployee();

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Male", null, "123 Main St");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.findById(employeeId);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    public void testSaveEmployee() {
        Employee employeeToSave = new Employee(null, "John", "Male", null, "123 Main St");
        Employee savedEmployee = new Employee(1L, "John", "Male", null, "123 Main St");

        when(employeeRepository.save(employeeToSave)).thenReturn(savedEmployee);

        Employee result = employeeService.saveEmployee(employeeToSave);

        assertEquals(1L, result.getId());
        verify(employeeRepository, times(1)).save(employeeToSave);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employeeToUpdate = new Employee(1L, "John", "Male", null, "123 Main St");

        when(employeeRepository.save(employeeToUpdate)).thenReturn(employeeToUpdate);

        Employee result = employeeService.updateEmployee(employeeToUpdate);

        assertEquals(1L, result.getId());
        verify(employeeRepository, times(1)).save(employeeToUpdate);
    }

    @Test
    public void testDeleteEmployee() {
        Long employeeId = 1L;

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}