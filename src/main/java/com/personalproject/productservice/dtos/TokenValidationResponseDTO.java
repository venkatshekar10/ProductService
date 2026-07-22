package com.personalproject.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenValidationResponseDTO {

    private boolean valid;
    private Long userId;

    public boolean isValid() { return valid; }
}