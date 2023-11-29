package com.example.springbootdev.service;

import com.example.springbootdev.dao.EmployeeDao;
import com.example.springbootdev.exception.ResourceNotFoundException;
import com.example.springbootdev.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }


    public Employee createEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    public ResponseEntity<Employee> getEmployeeById(Long id) {
        Employee employee =  employeeDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Employee not exist with Id: "+id));
        return ResponseEntity.ok(employee);

    }

    public ResponseEntity<Employee> updateEmployee(Long id, Employee employeeDetails) {
        Employee employee1 = employeeDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with Id:"+id));
        employee1.setFirstName(employeeDetails.getFirstName());
        employee1.setLastName(employeeDetails.getLastName());
        employee1.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = employeeDao.save(employee1);
        return ResponseEntity.ok(updatedEmployee);
    }

    public ResponseEntity<Map<String, Boolean>> deleteEmployee(Long id) {
        Employee employee2 = employeeDao.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not exist with Id:"+id));
        employeeDao.delete(employee2);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
