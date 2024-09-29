package com.SWP391.KoiXpress.Service;


import com.SWP391.KoiXpress.Entity.Role;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.DuplicateEntity;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.LoginRequest;
import com.SWP391.KoiXpress.Model.RegisterRequest;
import com.SWP391.KoiXpress.Model.LoginResponse;
import com.SWP391.KoiXpress.Model.RegisterResponse;
import com.SWP391.KoiXpress.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;
    public RegisterResponse register(RegisterRequest registerRequest){
        User user = modelMapper.map(registerRequest, User.class);
        try{
            String originpassword = user.getPassword();
            user.setPassword(passwordEncoder.encode(originpassword));
            user.setRole(Role.fromUsername(registerRequest.getUsername()));
            User newUser = userRepository.save(user);
            return modelMapper.map(newUser, RegisterResponse.class);
        }catch (Exception e){
            e.printStackTrace();
            if(e.getMessage().contains(user.getFullname())) {
                throw new DuplicateEntity("Duplicate fullName");
            }else if (e.getMessage().contains(user.getPhone())) {
                throw new DuplicateEntity("Duplicate phone");
            }else{
                throw new NotFoundException("Unknow Error error");
            }

        }

    }

    public LoginResponse login(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            ));
            User user = (User) authentication.getPrincipal();

            return modelMapper.map(user, LoginResponse.class);
        }catch(Exception e){
//            e.printStackTrace();
            throw new BadCredentialsException("UserName or password invalid!");
        }

    }

    public List<RegisterResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<RegisterResponse> registerResponses = users.stream().map(user -> modelMapper.map(user, RegisterResponse.class)).collect(Collectors.toList());
        return registerResponses;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
        }
        return user;
    }
}
