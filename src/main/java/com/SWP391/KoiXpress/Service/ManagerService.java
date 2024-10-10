package com.SWP391.KoiXpress.Service;


import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.DuplicateEntity;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.request.RegisterRequestManager;
import com.SWP391.KoiXpress.Model.request.UpdateRequestManager;
import com.SWP391.KoiXpress.Model.response.LoginResponse;
import com.SWP391.KoiXpress.Model.response.RegisterResponse;
import com.SWP391.KoiXpress.Model.response.UpdateResponse;
import com.SWP391.KoiXpress.Model.response.UserResponse;
import com.SWP391.KoiXpress.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;


    public RegisterResponse create(RegisterRequestManager registerRequestManager) {
        User user = modelMapper.map(registerRequestManager, User.class);
        try {
            String originpassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(originpassword));
            user.setRole(registerRequestManager.getRole());
            User newUser = userRepository.save(user);
            return modelMapper.map(newUser, RegisterResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains(user.getFullname())) {
                throw new DuplicateEntity("Duplicate fullName");
            } else if (e.getMessage().contains(user.getPhone())) {
                throw new DuplicateEntity("Duplicate phone");
            } else {
                throw new NotFoundException("Unknow Error ");
            }
        }
    }

    public UpdateResponse update (long userId, UpdateRequestManager updateRequestManager) {
        modelMapper.map(updateRequestManager, User.class);
        User oldUser = getUserById(userId);
        try {
            oldUser.setImage(updateRequestManager.getImage());
            oldUser.setRole(updateRequestManager.getRole());
            oldUser.setLoyaltyPoint(updateRequestManager.getLoyaltyPoint());
            oldUser.setDeleted(updateRequestManager.getUserstatus());
            User newUser = userRepository.save(oldUser);
            return modelMapper.map(newUser, UpdateResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Unknow Error");
            }
    }

    public LoginResponse delete(long userId) {
        try {
            User oldUser = getUserById(userId);
            oldUser.setDeleted(true);
            User newUser = userRepository.save(oldUser);
            return modelMapper.map(newUser, LoginResponse.class);

        } catch (Exception e) {
            // Xử lý các lỗi khác nếu cần
            e.printStackTrace();
            throw new EntityNotFoundException("User not found");
        }
    }

    public List<RegisterResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<RegisterResponse> registerResponses = users.stream().map(user -> modelMapper.map(user, RegisterResponse.class)).collect(Collectors.toList());
        return registerResponses;
    }

    private User getUserById(long id) {
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null) {
            throw new EntityNotFoundException("User not found!");
        }

        return oldUser;
    }
    public RegisterResponse getEachUserById(long id){
        User user = userRepository.findUserById(id);
        if(user == null){
            throw new EntityNotFoundException("User not found");
        }
        return modelMapper.map(user, RegisterResponse.class);
    }
}

