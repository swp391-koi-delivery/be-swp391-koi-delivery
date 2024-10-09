package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.request.BlogRequest;
import com.SWP391.KoiXpress.Model.response.BlogResponse;
import com.SWP391.KoiXpress.Model.response.UserResponse;
import com.SWP391.KoiXpress.Repository.BlogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;


    public BlogResponse createBlog(BlogRequest blogRequest){
        Blog blog = new Blog();
        User user = authenticationService.getCurrentUser();
        blog.setImg(blogRequest.getImg());
        blog.setPost(blogRequest.getPost());
        blog.setUser(user);
        blog= blogRepository.save(blog);
        //
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setUserResponse(userResponse);
        blogResponse.setBlogId(blog.getBlogId());
        blogResponse.setPost(blog.getPost());
        blogResponse.setImg(blog.getImg());

        return blogResponse;
    }

    public List<BlogResponse> getAllBlog() {
        List<BlogResponse> blogResponses = new ArrayList<>();

        List<Blog> blogs = blogRepository.findAll();
        for(Blog blog : blogs) {
            User user = blog.getUser();
            BlogResponse blogResponse = new BlogResponse();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setImg(blog.getImg());
            blogResponse.setPost(blog.getPost());
            blogResponse.setUserResponse(userResponse);
            blogResponses.add(blogResponse);
        }

        return blogResponses;
    }

    public Blog delete(long blogId) {
        Blog blog = getBlogById(blogId);
        if(blog == null){
            throw new NotFoundException("Can not found blog");
        }
            blog.setPoststatus(true);
            return blogRepository.save(blog);
    }
    public Blog update(long blogId, Blog blog){
        Blog oldBlog = getBlogById(blogId);
        if(oldBlog == null){
            throw new EntityNotFoundException("Can not update Blog");
        }else{
            oldBlog.setPost(blog.getPost());
            oldBlog.setImg(blog.getImg());
        }
        return blogRepository.save(oldBlog);
    }


    private Blog getBlogById (long blogId){
        Blog oldBlog = blogRepository.findBlogByBlogId(blogId);
        if (oldBlog == null){
            throw new EntityNotFoundException("Blog not found!");
        }
        if (oldBlog.getPoststatus()){
            throw new EntityNotFoundException("Blog not found!");
        }
        return oldBlog;
    }
}
