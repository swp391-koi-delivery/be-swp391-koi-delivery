package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.EmailDetail;
import com.SWP391.KoiXpress.Entity.Enum.EmailStatus;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.*;
import com.SWP391.KoiXpress.Model.request.User.CreateUserByManagerRequest;
import com.SWP391.KoiXpress.Model.request.User.UpdateCustomerRequest;
import com.SWP391.KoiXpress.Model.request.User.UpdateUserByManagerRequest;
import com.SWP391.KoiXpress.Model.response.Authen.LoginResponse;
import com.SWP391.KoiXpress.Model.response.Paging.PagedResponse;
import com.SWP391.KoiXpress.Model.response.User.*;
import com.SWP391.KoiXpress.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationService authenticationService;

    public UpdateCustomerResponse update(long userId, UpdateCustomerRequest updateCustomerRequest) {
        modelMapper.map(updateCustomerRequest, User.class);
        User oldUser = getUserById(userId);
        try {
            oldUser.setUsername(updateCustomerRequest.getUsername());

            String originPassword = updateCustomerRequest.getPassword();
            oldUser.setPassword(passwordEncoder.encode(originPassword));

            oldUser.setFullname(updateCustomerRequest.getFullname());

            oldUser.setImage(updateCustomerRequest.getImage());

            oldUser.setAddress(updateCustomerRequest.getAddress());

            oldUser.setPhone(updateCustomerRequest.getPhone());

            oldUser.setEmail(updateCustomerRequest.getEmail());

            //check mail truoc khi gui

            User newUser = userRepository.save(oldUser);
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setUser(newUser);
            emailDetail.setSubject("Update Email");
            emailDetail.setLink("#");
            boolean emailSend = emailService.sendEmailVerify(emailDetail);
            if (emailSend) {
                //gui email xac thuc
                newUser.setEmailStatus(EmailStatus.VERIFIED);
            } else {
                newUser.setEmailStatus(EmailStatus.NOT_VERIFIED);
                throw new EmailNotVerifiedException("Email not verify. please provide a valid email.");
            }
            userRepository.save(newUser);
            return modelMapper.map(newUser, UpdateCustomerResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains(updateCustomerRequest.getFullname())) {
                throw new DuplicateEntity("Duplicate fullName");
            } else if (e.getMessage().contains(updateCustomerRequest.getPhone())) {
                throw new DuplicateEntity("Duplicate phone");
            } else {
                throw new NullPointerException("Can not find old user to update!");
            }
        }
    }

    public DeleteUserByUserResponse deleteByUser(long userId) {
        try {
            User oldUser = getUserById(userId);
            oldUser.setDeleted(true);
            User newUser = userRepository.save(oldUser);
            return modelMapper.map(newUser, DeleteUserByUserResponse.class);

        } catch (Exception e) {
            // Xử lý các lỗi khác nếu cần
            e.printStackTrace();
            throw new EntityNotFoundException("User not found");
        }
    }

    public CreateUserByManagerResponse create(CreateUserByManagerRequest createUserByManagerRequest) {
        User user = modelMapper.map(createUserByManagerRequest, User.class);
        try {
            String originPassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(originPassword));
            user.setRole(createUserByManagerRequest.getRole());
            User newUser = userRepository.save(user);
            return modelMapper.map(newUser, CreateUserByManagerResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains(user.getFullname())) {
                throw new DuplicateEntity("Duplicate fullName");
            } else if (e.getMessage().contains(user.getPhone())) {
                throw new DuplicateEntity("Duplicate phone");
            } else {
                throw new NotFoundException("Unknown Error ");
            }
        }
    }

    public UpdateCustomerResponse update(long userId, UpdateUserByManagerRequest updateUserByManagerRequest) {
        modelMapper.map(updateUserByManagerRequest, User.class);
        User currentUser = authenticationService.getCurrentUser();
        User oldUser = getUserById(userId);
        if (currentUser == oldUser) {
            throw new AuthException("You cant update because this account is using");
        }
        try {
            oldUser.setImage(updateUserByManagerRequest.getImage());
            oldUser.setRole(updateUserByManagerRequest.getRole());
            oldUser.setLoyaltyPoint(updateUserByManagerRequest.getLoyaltyPoint());
            oldUser.setDeleted(updateUserByManagerRequest.isDeleted());
            User newUser = userRepository.save(oldUser);
            return modelMapper.map(newUser, UpdateCustomerResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Unknown Error");
        }
    }

    public DeleteUserByManagerResponse deleteByManager(long userId) {
        try {
            User oldUser = getUserById(userId);
            oldUser.setDeleted(true);
            User newUser = userRepository.save(oldUser);
            return modelMapper.map(newUser, DeleteUserByManagerResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("User not found");
        }
    }

    public PagedResponse<LoginResponse> getAllUser(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageRequest);
        List<LoginResponse> loginResponses = userPage.getContent().stream()
                .map(user -> modelMapper.map(user, LoginResponse.class))
                .collect(Collectors.toList());
        return new PagedResponse<>(
                loginResponses,
                page,
                size,
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isLast()
        );
    }


    public UserResponse getEachUserById(long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return modelMapper.map(user, UserResponse.class);
    }


    private User getUserById(long userId) {
        User oldUser = userRepository.findUserById(userId);
        if (oldUser == null) {
            throw new EntityNotFoundException("User not found!");
        }
        if (oldUser.isDeleted()) {
            throw new EntityNotFoundException("User not found!");
        }
        return oldUser;
    }



}
