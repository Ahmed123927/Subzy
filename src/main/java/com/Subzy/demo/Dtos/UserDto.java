package com.Subzy.demo.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String role;
}
