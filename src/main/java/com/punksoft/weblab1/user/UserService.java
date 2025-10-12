package com.punksoft.weblab1.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.punksoft.weblab1.security.systemuser.Role;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user <" + username + ">"));
    }

    public UserEntity getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user <" + id + ">"));
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean banUser(int userId, String currentAdminUsername) {
        userRepository.findByUsername(currentAdminUsername).orElseThrow();
        var user = userRepository.findById(userId).orElseThrow();

        if (user.getRoles().contains(Role.ROLE_ADMIN.name())) {
            return false;
        }

        user.setBanned(true);
        userRepository.save(user);

        return true;
    }

    public void unbanUser(int userId) {
        var user = userRepository.findById(userId).orElseThrow();

        if (user.isBanned()) {
            user.setBanned(false);
            userRepository.save(user);
        }
    }

    public void updateProfile(String username, UserEntity updated) {
        var user = getUserByUsername(username);

        user.setIdLabel(updated.getIdLabel());
        user.setName(updated.getName());
        user.setAvatarPath(updated.getAvatarPath());

        if (!updated.getPassword().isBlank()) {
            var encryptedPwd = passwordEncoder.encode(updated.getPassword());
            user.setPassword(encryptedPwd);
        }

        userRepository.saveAndFlush(user);
    }

    public List<String> getAvailableAvatars() {
        var avatarsDir = Paths.get("src/main/resources/static/avatars");
        if (!Files.exists(avatarsDir)) {
            return List.of();
        }

        try (var paths = Files.list(avatarsDir)) {
            return paths.filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .filter(name -> name.endsWith(".png"))
                        .collect(Collectors.toList());
        } catch (IOException e) {
            return List.of();
        }
    }
}