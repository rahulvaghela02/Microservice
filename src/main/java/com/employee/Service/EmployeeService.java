package com.employee.Service;

import com.employee.Payload.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createNewEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployee();

    EmployeeDto updateEmployee(EmployeeDto employeeDto, long id);

    void deleteById(long id);

    EmployeeDto getEmployeeById(long id);
}
