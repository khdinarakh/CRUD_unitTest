package com.example.demo.controllers;


import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> findAllEmployee(){
        return employeeService.findAllEmployee();
    }

    @GetMapping("/{id}")
    public Optional<Employee>  findEmployeeById(@PathVariable("id") Long id){
        return employeeService.findById(id);
    }

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employeeEntity) {
        return employeeService.saveEmployee(employeeEntity);
    }

    @PutMapping
    public Employee updateEmployee(@RequestBody Employee employee){
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
    }
}
