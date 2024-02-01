package org.example.dto;

public record RegisterDto(
        String firstName,
        String lastName,
        String group,
        String nameTeam,
        String telegram,
        String email
) {
}
