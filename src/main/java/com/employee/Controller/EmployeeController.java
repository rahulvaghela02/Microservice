package com.employee.Controller;

import com.employee.Payload.EmployeeDto;
import com.employee.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.createNewEmployee(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public List<EmployeeDto> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,@PathVariable("id") long id){
        EmployeeDto employeeDto1 = employeeService.updateEmployee(employeeDto, id);
        return new ResponseEntity<>(employeeDto1,HttpStatus.OK);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteById(@PathVariable("id") long id){
        employeeService.deleteById(id);
        return ResponseEntity.ok("Employee Entity Deleted Successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id")long id){
        EmployeeDto employeeById = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeById,HttpStatus.OK);
    }

}
