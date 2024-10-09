package com.SWP391.KoiXpress.Model.request;

import jakarta.validation.constraints.NotBlank;

public class PolicyRequest {
    @NotBlank(message = "Title can not be blank")
    String title;
    @NotBlank(message = "Post can not be blank")
    String post;
}
