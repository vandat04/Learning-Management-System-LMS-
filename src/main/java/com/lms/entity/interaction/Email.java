package com.lms.entity.interaction;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Email {
    @NotBlank(message = "Email can not blank")
    private String toEmail;
    @NotBlank(message = "Subject can not blank")
    private String subject;
    @NotBlank(message = "Body can not blank")
    private String body;

}
