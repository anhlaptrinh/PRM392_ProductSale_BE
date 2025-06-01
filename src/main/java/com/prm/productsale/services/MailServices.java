package com.prm.productsale.services;

import com.prm.productsale.dto.DataMailDTO;
import jakarta.mail.MessagingException;

public interface MailServices {
  void sendHtmlMail(DataMailDTO dataMailDTO, String templateName) throws MessagingException;
}
