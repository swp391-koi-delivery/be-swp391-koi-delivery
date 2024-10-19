package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Model.request.Blog.CreateBlogRequest;
import com.SWP391.KoiXpress.Model.response.Blog.AllBlogResponse;
import com.SWP391.KoiXpress.Model.response.Blog.CreateBlogResponse;
import com.SWP391.KoiXpress.Model.response.Blog.DeleteBlogResponse;
import com.SWP391.KoiXpress.Model.response.Blog.UpdateBlogResponse;
import com.SWP391.KoiXpress.Model.response.User.EachUserResponse;
import com.SWP391.KoiXpress.Repository.BlogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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


    public CreateBlogResponse createBlog(CreateBlogRequest createBlogRequest) {
        Blog blog = new Blog();
        User user = authenticationService.getCurrentUser();
        blog.setImg(createBlogRequest.getImg());
        blog.setPost(createBlogRequest.getPost());
        blog.setUser(user);
        blogRepository.save(blog);
        //
        EachUserResponse eachUserResponse = modelMapper.map(user, EachUserResponse.class);
        CreateBlogResponse createBlogResponse = new CreateBlogResponse();
        createBlogResponse.setEachUserResponse(eachUserResponse);
        createBlogResponse.setBlogId(blog.getId());
        createBlogResponse.setPost(blog.getPost());
        createBlogResponse.setImg(blog.getImg());

        return createBlogResponse;
    }

    public List<AllBlogResponse> getAllBlog(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Blog> blogsPage = blogRepository.findAll(pageRequest);

        List<AllBlogResponse> allBlogResponses = new ArrayList<>();
        for (Blog blog : blogsPage.getContent()) {
            User user = blog.getUser();
            AllBlogResponse allBlogResponse = new AllBlogResponse();
            EachUserResponse eachUserResponse = modelMapper.map(user, EachUserResponse.class);
            allBlogResponse.setBlogId(blog.getId());
            allBlogResponse.setImg(blog.getImg());
            allBlogResponse.setPost(blog.getPost());
            allBlogResponse.setEachUserResponse(eachUserResponse);
            allBlogResponses.add(allBlogResponse);
        }

        return allBlogResponses;
    }

    public DeleteBlogResponse delete(long blogId) {
        Blog blog = getBlogById(blogId);
        blog.setDeleted(true);
        blogRepository.save(blog);
        return modelMapper.map(blog, DeleteBlogResponse.class);
    }

    public UpdateBlogResponse update(long blogId, Blog blog) {
        Blog oldBlog = getBlogById(blogId);
        oldBlog.setPost(blog.getPost());
        oldBlog.setImg(blog.getImg());
        blogRepository.save(oldBlog);
        return modelMapper.map(oldBlog, UpdateBlogResponse.class);
    }


    private Blog getBlogById(long blogId) {
        Blog oldBlog = blogRepository.findBlogById(blogId);
        if (oldBlog == null) {
            throw new EntityNotFoundException("Blog not found!");
        }
        if (oldBlog.isDeleted()) {
            throw new EntityNotFoundException("Blog not found!");
        }
        return oldBlog;
    }
}
