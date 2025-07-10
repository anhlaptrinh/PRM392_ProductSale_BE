package com.prm.productsale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "UserID")
  int id;

  @Column(name = "Username")
  String username;

  @Size(min = 8,message = "INVALID_PASSWORD")
  @Column(name = "PasswordHash")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  List<CartEntity> carts;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  List<OrderEntity> orders;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  List<WishlistEntity> wishlist;
}
