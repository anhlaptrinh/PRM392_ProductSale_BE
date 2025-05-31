package com.prm.productsale.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int UserID;

  @Column(name = "Username")
  private String username;

  @Size(min = 8,message = "INVALID_PASSWORD")
  @Column(name = "PasswordHash")
  private String password;

  @Email(message = "Email is incorrect!")
  @NotBlank(message = "Email is not blank!")
  @Size(max = 255, message = "Email, max is 255")
  @Column(name = "Email")
  private String email;

  @Column(name = "PhoneNumber")
  private String phoneNumber;

  @Column(name = "Address")
  private String address;

  @Column(name = "Role")
  private String role;
}
