package com.coderuck.tasksubmissionservice.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private Long id;
    private String password;
    private String email;
    private String role;
    private String fullName;
}
