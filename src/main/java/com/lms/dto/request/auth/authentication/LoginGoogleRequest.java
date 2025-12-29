package com.lms.dto.request.auth.authentication;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoginGoogleRequest {
    private String idToken;
}
