package org.example.promenergosvet.service.user;

import org.example.promenergosvet.entity.User;
import org.example.promenergosvet.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String surname) throws UsernameNotFoundException {
        User user = userRepository.findBySurname(surname);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getSurname())
                .password(user.getTelephone())
                .build();
    }

}
