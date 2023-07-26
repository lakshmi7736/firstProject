package com.Ecom.Bee.Baa.DTO;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "Please enter valid name")
    private String name;

    @NotEmpty(message = "Please enter valid email")
    private String email;

    @NotEmpty(message = "Please enter valid password")
    private String password;

}
