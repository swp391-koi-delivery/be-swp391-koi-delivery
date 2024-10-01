package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.FeedBack;
import com.SWP391.KoiXpress.Entity.Enum.Role;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.DuplicateEntity;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.Request.RegisterRequest;
import com.SWP391.KoiXpress.Model.Request.UpdateRequest;
import com.SWP391.KoiXpress.Model.Response.LoginResponse;
import com.SWP391.KoiXpress.Model.Response.RegisterResponse;
import com.SWP391.KoiXpress.Model.Response.UpdateResponse;
import com.SWP391.KoiXpress.Repository.BlogRepository;
import com.SWP391.KoiXpress.Repository.FeedBackRepository;
import com.SWP391.KoiXpress.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    //------------------------------CRUD---------------USER-------------------------------------//
        @Autowired
        UserRepository userRepository;

        @Autowired
        ModelMapper modelMapper;

        @Autowired
        PasswordEncoder passwordEncoder;

        public RegisterResponse create (RegisterRequest registerRequest){
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

        public UpdateResponse update (long userId, UpdateRequest updateRequest) {
            modelMapper.map(updateRequest, User.class);
            User oldUser = userRepository.findUserByUserId(userId);
            try {
                oldUser.setUsername(updateRequest.getUsername());
                oldUser.setRole(Role.fromUsername(updateRequest.getUsername()));
                String originpassword = updateRequest.getPassword();
                oldUser.setPassword(passwordEncoder.encode(originpassword));
                oldUser.setFullname(updateRequest.getFullname());
                oldUser.setImage(updateRequest.getImage());
                oldUser.setAddress(updateRequest.getAddress());
                oldUser.setPhone(updateRequest.getPhone());
                oldUser.setEmail(updateRequest.getEmail());
                oldUser.setLoyaltyPoint(updateRequest.getLoyaltyPoint());
                User newUser = userRepository.save(oldUser);
                return modelMapper.map(newUser, UpdateResponse.class);
            } catch (Exception e) {
//                e.printStackTrace();
                if (e.getMessage().contains(updateRequest.getFullname())) {
                    throw new DuplicateEntity("Duplicate fullName");
                } else if (e.getMessage().contains(updateRequest.getPhone())) {
                    throw new DuplicateEntity("Duplicate phone");
                } else {
                    throw new NullPointerException("Can not find old user to update!");
                }
            }
        }

    public List<RegisterResponse> getCurrentUser () {
        List<User> users = userRepository.findUserByUserstatusFalse();
        List<RegisterResponse> registerResponses = users.stream().map(user -> modelMapper.map(user,RegisterResponse.class)).collect(Collectors.toList());
        return registerResponses;
    }

        public LoginResponse delete (long userId){
        try{
            User oldUser = getUserById(userId);
            oldUser.setUserstatus(true);
            User newUser = userRepository.save(oldUser);
            return modelMapper.map(newUser,LoginResponse.class);

        } catch (Exception e) {
            // Xử lý các lỗi khác nếu cần
            e.printStackTrace();
            throw new EntityNotFoundException("User not found");
        }
        }

        private User getUserById ( long userId){
        User oldUser = userRepository.findUserByUserId(userId);
        if (oldUser == null)
            throw new EntityNotFoundException("User not found!");
//            if (oldUser.getUserstatus()=="BLOCK")throw new EntityNotFoundException("User not found!");
        return oldUser;
    }
    //-------------------------------------------------------------------------------------------//
    @Autowired
    BlogRepository blogRepository;

    public Blog generateBlog(Blog blog) {
        Blog newBlog = blogRepository.save(blog);
        return newBlog;
    }
    @Autowired
    FeedBackRepository feedBackRepository;

    public FeedBack generateFeedBack(FeedBack feedBack) {
        FeedBack newFeedBack = feedBackRepository.save(feedBack);
        return newFeedBack;
    }


}
