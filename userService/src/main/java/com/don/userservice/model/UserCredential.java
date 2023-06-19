package com.don.userservice.model;

import com.don.userservice.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "User name cannot be null or empty")
    @NotNull(message = "i am don")
    private String userName;

    @Email(message = "please enter valid email")
    private String email;

//    @Min(value = 5,message = "minimum password length should be 5")
    private String password;
    private String role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
