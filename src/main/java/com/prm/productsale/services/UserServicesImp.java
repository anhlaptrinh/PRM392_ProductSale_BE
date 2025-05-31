package com.prm.productsale.services;

import com.prm.productsale.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImp implements UserServices {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  PasswordEncoder passwordEncoder;


}
