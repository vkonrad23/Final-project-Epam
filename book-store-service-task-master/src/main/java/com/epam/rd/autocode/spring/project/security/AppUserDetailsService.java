package com.epam.rd.autocode.spring.project.security;

import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.repo.ClientRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    public AppUserDetailsService(EmployeeRepository employeeRepository, ClientRepository clientRepository) {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(username).orElse(null);
        if (employee != null) {
            return new User(employee.getEmail(), employee.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        }

        Client client = clientRepository.findByEmail(username).orElse(null);
        if (client != null) {
            return new User(client.getEmail(), client.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
