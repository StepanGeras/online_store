package org.example.promenergosvet.service;

import lombok.AllArgsConstructor;
import org.example.promenergosvet.entity.Admin;
import org.example.promenergosvet.repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepo.save(admin);
    }



}
