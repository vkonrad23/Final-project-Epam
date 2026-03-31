package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.EmployeeDTO;
import com.epam.rd.autocode.spring.project.exception.AlreadyExistException;
import com.epam.rd.autocode.spring.project.exception.NotFoundException;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .toList();
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + email));
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployeeByEmail(String email, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + email));
        modelMapper.map(employeeDTO, employee);
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }

    @Override
    public void deleteEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + email));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        log.info("Registering employee: {}", employeeDTO.getEmail());
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new AlreadyExistException("Employee already exists: " + employeeDTO.getEmail());
        }
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }
}
