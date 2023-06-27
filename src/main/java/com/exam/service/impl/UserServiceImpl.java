package com.exam.service.impl;

import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repository.RoleRepository;
import com.exam.repository.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Creating user
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
         User localuser = this.userRepository.findByUsername(user.getUsername());
        if (localuser != null){
            System.out.println("User is already there !!");
            throw new Exception("User is already present !!");
        }else {
            //create user
            // fetching each role from userRoles
            for (UserRole ur:userRoles)
            {
                roleRepository.save(ur.getRole());
            }
            //setting all userRoles in user (all roles has been assigned to user)
            user.getUserRoles().addAll(userRoles);
            // saving user and userRoles will automatic save with it.
            localuser = this.userRepository.save(user);
        }
        return localuser;
    }

    //getting user by username
    @Override
    public User getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    // delete user
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public String updateUserService(User user, Long id) {
        Optional<User> result = this.userRepository.findById(id);
        if(result.isEmpty())
            return "User not found";

        var existingUserResult  = result.get();
        existingUserResult.setUsername(user.getUsername());
        existingUserResult.setPassword(user.getPassword());
        existingUserResult.setFirstName(user.getFirstName());
        existingUserResult.setLastName(user.getLastName());
        existingUserResult.setEmail(user.getEmail());
        existingUserResult.setPhone(user.getPhone());
        existingUserResult.setEnabled(user.isEnabled());
        existingUserResult.setProfile(user.getProfile());
        userRepository.save(existingUserResult);
        return "User updated successfully ..!!";























    }
}
