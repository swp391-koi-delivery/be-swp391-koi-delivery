package com.SWP391.KoiXpress.Model.response;

import lombok.Data;

@Data
public class EmailResponse {
    private boolean format_valid;
    private boolean mx_found;
}
