package org.example.dto;

public record RegisterRequest(
        String firstName,
        String lastName,
        String collegeGroup,
        String nameTeam,
        String telegram,
        String email
) {
}
