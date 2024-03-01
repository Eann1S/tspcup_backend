package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @Pattern(regexp = "^[А-Яа-яёЁ]+(-[А-Яа-яёЁ]+)?$", message = "{validation.firstName.pattern}")
        String firstName,
        @Pattern(regexp = "^[А-Яа-яёЁ]+(-[А-Яа-яёЁ]+)?$", message = "{validation.lastName.pattern}")
        String lastName,
        @NotBlank(message = "{validation.collegeGroup.notBlank}")
        String collegeGroup,
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{validation.nameTeam.pattern}")
        String nameTeam,
        @Pattern(regexp = "^@[A-Za-z0-9]+$", message = "{validation.telegram.pattern}")
        String telegram,
        @Email(message = "{validation.email.notValid}")
        @NotBlank(message = "{validation.email.notBlank}")
        String email
) {
}
