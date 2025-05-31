package com.prm.productsale.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int UserID;

  @Column(name = "Username")
  String username;

  @Size(min = 8,message = "INVALID_PASSWORD")
  @Column(name = "PasswordHash")
  String password;

  @Email(message = "Email is incorrect!")
  @NotBlank(message = "Email is not blank!")
  @Size(max = 255, message = "Email, max is 255")
  @Column(name = "Email")
  String email;

  @Column(name = "PhoneNumber")
  String phoneNumber;

  @Column(name = "Address")
  String address;

  @Column(name = "Role")
  String role;
}
