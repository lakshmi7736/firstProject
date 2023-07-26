package com.Ecom.Bee.Baa.Models.TOKEN;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtRequest {

    private String email;

    private String password;
}
