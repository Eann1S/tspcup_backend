package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"firstName", "lastName"}),
                @UniqueConstraint(columnNames = "telegram"),
                @UniqueConstraint(columnNames = "email")
        },
        indexes = {
                @Index(columnList = "firstName, lastName"),
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
    private String group;
    private String nameTeam;
    private String telegram;
    private String email;
}
