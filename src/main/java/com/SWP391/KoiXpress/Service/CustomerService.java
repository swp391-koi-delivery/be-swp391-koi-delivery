package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.NotEntity.EmailDetail;
import com.SWP391.KoiXpress.Entity.Enum.EmailStatus;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.DuplicateEntity;
import com.SWP391.KoiXpress.Exception.EmailNotVerifiedException;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Model.request.UpdateRequest;
import com.SWP391.KoiXpress.Model.response.LoginResponse;
import com.SWP391.KoiXpress.Model.response.UpdateResponse;
import com.SWP391.KoiXpress.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    //------------------------------CRUD---------------CUSTOMER-------------------------------------//
        @Autowired
        UserRepository userRepository;

        @Autowired
        ModelMapper modelMapper;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        EmailService emailService;

        public UpdateResponse update (long userId, UpdateRequest updateRequest) {
            modelMapper.map(updateRequest, User.class);
            User oldUser = getUserById(userId);
            try {
                oldUser.setUsername(updateRequest.getUsername());

                String originpassword = updateRequest.getPassword();
                oldUser.setPassword(passwordEncoder.encode(originpassword));

                oldUser.setFullname(updateRequest.getFullname());

                oldUser.setImage(updateRequest.getImage());

                oldUser.setAddress(updateRequest.getAddress());

                oldUser.setPhone(updateRequest.getPhone());

                oldUser.setEmail(updateRequest.getEmail());

                //check mail truoc khi gui

                User newUser = userRepository.save(oldUser);
                EmailDetail emailDetail = new EmailDetail();
                emailDetail.setUser(newUser);
                emailDetail.setSubject("Update Email");
                emailDetail.setLink("#");
                boolean emailSend = emailService.sendEmail(emailDetail);
                if(emailSend){
                    //gui email xac thuc
                    newUser.setEmailStatus(EmailStatus.Verified);
                }else{
                    newUser.setEmailStatus(EmailStatus.NotVerified);
                    throw new EmailNotVerifiedException("Email not verify. please provide a valid email.");
                }
                userRepository.save(newUser);
                return modelMapper.map(newUser, UpdateResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getMessage().contains(updateRequest.getFullname())) {
                    throw new DuplicateEntity("Duplicate fullName");
                } else if (e.getMessage().contains(updateRequest.getPhone())) {
                    throw new DuplicateEntity("Duplicate phone");
                } else {
                    throw new NullPointerException("Can not find old user to update!");
                }
            }
        }



//    public List<RegisterResponse> getCurrentUser () {
//        List<User> users = userRepository.findUserByUserstatusFalse();
//        List<RegisterResponse> registerResponses = users.stream().map(user -> modelMapper.map(user,RegisterResponse.class)).collect(Collectors.toList());
//        return registerResponses;
//    }

    public LoginResponse delete(long userId) {
        try {
            User oldUser = getUserById(userId);
            oldUser.setUserstatus(true);
            User newUser = userRepository.save(oldUser);
            return modelMapper.map(newUser, LoginResponse.class);

        } catch (Exception e) {
            // Xử lý các lỗi khác nếu cần
            e.printStackTrace();
            throw new EntityNotFoundException("User not found");
        }
    }

        private User getUserById ( long userId){
        User oldUser = userRepository.findUserByUserId(userId);
        if (oldUser == null){
            throw new EntityNotFoundException("User not found!");
        }
            if (oldUser.getUserstatus()){
                throw new EntityNotFoundException("User not found!");
            }
        return oldUser;
    }
    //-------------------------------------------------------------------------------------------//



}
