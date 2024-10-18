package com.SWP391.KoiXpress.Service;


import com.SWP391.KoiXpress.Entity.EmailDetail;
import com.SWP391.KoiXpress.Entity.Enum.EmailStatus;
import com.SWP391.KoiXpress.Entity.Enum.Role;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.DuplicateEntity;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;

import com.SWP391.KoiXpress.Model.request.*;
import com.SWP391.KoiXpress.Model.response.LoginGoogleResponse;
import com.SWP391.KoiXpress.Model.response.LoginResponse;
import com.SWP391.KoiXpress.Model.response.RegisterResponse;
import com.SWP391.KoiXpress.Repository.AuthenticationRepository;
import com.SWP391.KoiXpress.Repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service

public class AuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationRepository authenticationRepository;

    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);

        try {
            String originpassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(originpassword));
            user.setRole(Role.CUSTOMER);

            User newUser = userRepository.save(user);
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setUser(newUser);
            emailDetail.setSubject("Verify your email");
            emailDetail.setLink("#");
            boolean emailSent = emailService.sendEmailVerify(emailDetail);
            if (emailSent) {
                newUser.setEmailStatus(EmailStatus.VERIFIED);
                userRepository.save(newUser);
            }
            return modelMapper.map(newUser, RegisterResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains(user.getEmail())) {
                throw new DuplicateEntity("Duplicate email");
            } else if (e.getMessage().contains(user.getPhone())) {
                throw new DuplicateEntity("Duplicate phone");
            } else {
                throw new NotFoundException("Unknow Error error");
            }

        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));
            User user = (User) authentication.getPrincipal();
            LoginResponse loginResponse = modelMapper.map(user, LoginResponse.class);
            loginResponse.setToken(tokenService.generateToken(user));
            return loginResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("UserName or password invalid!");
        }

    }

    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findUserByEmail(forgotPasswordRequest.getEmail());
        if (user == null) {
            throw new EntityNotFoundException("Cant find your email account");
        }
        String token = tokenService.generateToken(user);
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setUser(user);
        emailDetail.setSubject("Reset Password");
        emailDetail.setLink("http://transportkoifish.online/reset-password?token=" + token);
        emailService.sendEmailResetPassword(emailDetail);

    }

    public User resetPassword(PasswordResetRequest passwordResetRequest) {
        User user = getCurrentUser();
        user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
        try {
            userRepository.save(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    //    public List<RegisterResponse> getAllUser() {
//        List<User> users = userRepository.findAll();
//        List<RegisterResponse> registerResponses = users.stream().map(user -> modelMapper.map(user, RegisterResponse.class)).collect(Collectors.toList());
//        return registerResponses;
//    }
    //security method
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
        }
        return user;
    }

    //ai đang call request này ( role)
    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticationRepository.findUserById(user.getId());
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    public LoginResponse createLoginResponse(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(user.getEmail());
        loginResponse.setFullname(user.getFullname());
        loginResponse.setToken(tokenService.generateToken(user));
        return loginResponse;
    }


    public LoginGoogleResponse loginGoogle(LoginGoogleRequest loginGoogleRequest) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(loginGoogleRequest.getToken());
            String email = decodedToken.getEmail();
            User user = userRepository.findUserByEmail(email);
            if (user == null) {
                User newUser = new User();
                newUser.setFullname(decodedToken.getName() != null ? decodedToken.getName() : "Unknown User");
                newUser.setEmail(email);
                newUser.setUsername(email);
                newUser.setRole(Role.CUSTOMER);
                newUser.setPassword(null);
                newUser.setPhone(null);
                newUser.setEmailStatus(EmailStatus.VERIFIED);
                userRepository.save(newUser);
                return new LoginGoogleResponse(
                        tokenService.generateToken(newUser),
                        newUser.getUsername()
                );
            }
            return new LoginGoogleResponse(
                    tokenService.generateToken(user),
                    user.getUsername()
            );
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Google ID token.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to log in with Google.");
        }
    }
}


