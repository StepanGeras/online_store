package org.example.promenergosvet.service.user;

import jakarta.transaction.Transactional;
import org.example.promenergosvet.entity.user.PasswordResetToken;
import org.example.promenergosvet.entity.user.User;
import org.example.promenergosvet.repo.user.PasswordResetTokenRepo;
import org.example.promenergosvet.repo.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepo passwordResetTokenRepo;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, PasswordResetTokenRepo passwordResetTokenRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepo = passwordResetTokenRepo;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void save(User user) {
        user.setRoles(Set.of(User.Role.ROLE_USER));
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        myToken.setExpiryDate(calculateExpiryDate());

        if (passwordResetTokenRepo.findTokenByUserId(user.getId()) != null){
            passwordResetTokenRepo.deleteByUserId(user.getId());
        }
        passwordResetTokenRepo.save(myToken);
    }

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        return calendar.getTime();
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
