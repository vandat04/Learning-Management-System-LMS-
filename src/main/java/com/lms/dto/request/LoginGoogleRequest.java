package com.lms.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoginGoogleRequest {
    private String idToken;
}
