package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.request.BlogRequest;
import com.SWP391.KoiXpress.Repository.BlogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;


    public Blog createBlog(BlogRequest blogRequest){
        Blog blog = new Blog();
        blog.setImg(blogRequest.getImg());
        blog.setPost(blogRequest.getPost());
        User user = authenticationService.getCurrentUser();
        blog.setUser(user);
        return blogRepository.save(blog);
    }

    public List<Blog> getAllBlog() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs;
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
        if(oldBlog==null){
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
