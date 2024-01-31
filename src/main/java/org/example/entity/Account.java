package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(
        name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "group"),
                @UniqueConstraint(columnNames = "nameTeam"),
                @UniqueConstraint(columnNames = "telegram"),
                @UniqueConstraint(columnNames = "email")
        },
        indexes = {
                @Index(columnList = "group"),
                @Index(columnList = "nameTeam"),
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
