package com.employee.Service.Serviceimpl;

import com.employee.Entity.Employee;
import com.employee.Payload.EmployeeDto;
import com.employee.Repository.EmployeeRepository;
import com.employee.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        Employee employee = mapToEntity(employeeDto);
        Employee save = employeeRepository.save(employee);
        return mapToDto(save);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> all = employeeRepository.findAll();
        return all.stream().map(data -> mapToDto(data)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto, long id) {
        Optional<Employee> byId = employeeRepository.findById(id);
        Employee employee = byId.get();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());

        Employee updateEmployee = employeeRepository.save(employee);

        return mapToDto(updateEmployee);

    }


    @Override
    public void deleteById(long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDto getEmployeeById(long id) {
        Optional<Employee> byId = employeeRepository.findById(id);
        Employee employee = byId.get();
        return mapToDto(employee);
    }


    public Employee mapToEntity(EmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        return employee;
    }
    public EmployeeDto mapToDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        return employeeDto;
    }

}
