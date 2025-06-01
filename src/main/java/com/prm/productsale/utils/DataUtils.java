package com.prm.productsale.utils;

import java.security.SecureRandom;

public class DataUtils {
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
  private static final SecureRandom RANDOM = new SecureRandom();
  public static String generateAndHashPassword(int length) {
    StringBuilder password = new StringBuilder();

    for (int i = 0; i < length; i++) {
      int index = RANDOM.nextInt(CHARACTERS.length());
      password.append(CHARACTERS.charAt(index));
    }

    return password.toString();
  }
}
