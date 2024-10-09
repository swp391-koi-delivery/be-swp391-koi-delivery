package com.SWP391.KoiXpress.Model.response;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BlogResponse {
    long blogId;
    String img;
    String post;
    UserResponse userResponse;
}
