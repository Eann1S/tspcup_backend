package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"first_name", "last_name"}),
                @UniqueConstraint(columnNames = "telegram"),
                @UniqueConstraint(columnNames = "email")
        },
        indexes = {
                @Index(columnList = "first_name, last_name"),
                @Index(columnList = "telegram"),
                @Index(columnList = "email")
        }
)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String firstName;
    private String lastName;
    private String collegeGroup;
    private String nameTeam;
    private String telegram;
    private String email;
}
