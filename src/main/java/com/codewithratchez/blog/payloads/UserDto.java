package com.codewithratchez.blog.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    @JsonIgnore
    private int id;
    @NotEmpty
    @NotBlank
    @Size(min = 4, message = "Username must be have min of 4 characters")
    private String name;
    @Email(message = "Email address is not valid")
    @NotBlank
    private String email;

//    @NotEmpty
//    @NotBlank
//    @JsonIgnore
//    @Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars")
//    private String password;


    private String about;

    @JsonIgnore
    private String bloggerId;

}
