package com.SWP391.KoiXpress.Model.response.Blog;

import com.SWP391.KoiXpress.Entity.User;
import lombok.Data;

@Data
public class UpdateBlogResponse {
    long id;
    String img;
    String post;
    boolean isDeleted =false;
    User user;
}
