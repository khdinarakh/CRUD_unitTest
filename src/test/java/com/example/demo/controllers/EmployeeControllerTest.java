package com.example.demo.controllers;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@WebMvcTest(EmployeeController.class)
@ActiveProfiles("test")

class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(employeeController).build();
    }


    @Test
    void testFindAllEmployee() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John", "Male", new Date(), "123 Main St"));
        employees.add(new Employee(2L, "Jane", "Female", new Date(), "456 Elm St"));

        mockMvc.perform(MockMvcRequestBuilders.get("/employee")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));

        verify(employeeService, times(1)).findAllEmployee();

    }

    @Test
    public void testFindEmployeeById() throws Exception {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Male", new Date(), "123 Main St");

        when(employeeService.findById(employeeId)).thenReturn(Optional.of(employee));

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));

        verify(employeeService, times(1)).findById(employeeId);
    }


    @Test
    public void testSaveEmployee() throws Exception {
        Employee employee = new Employee(null, "John", "Male", new Date(), "123 Main St");

        when(employeeService.saveEmployee(employee)).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"gender\":\"Male\",\"dateOfBirth\":\"2023-09-17\",\"address\":\"123 Main St\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));

        verify(employeeService, times(1)).saveEmployee(employee);
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employee = new Employee(1L, "John", "Male", new Date(), "123 Main St");

        when(employeeService.updateEmployee(employee)).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.put("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"John\",\"gender\":\"Male\",\"dateOfBirth\":\"2023-09-17\",\"address\":\"123 Main St\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));

        verify(employeeService, times(1)).updateEmployee(employee);
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Long employeeId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/{id}", employeeId))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }
}