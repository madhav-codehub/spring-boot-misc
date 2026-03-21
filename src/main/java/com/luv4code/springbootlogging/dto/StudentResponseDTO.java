package com.luv4code.springbootlogging.dto;

import lombok.Builder;

@Builder
public record StudentResponseDTO(
        String firstName,
        String lastName,
        String email
) {
}
