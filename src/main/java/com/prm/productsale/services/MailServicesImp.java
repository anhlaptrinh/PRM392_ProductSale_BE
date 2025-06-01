package com.prm.productsale.services;

import com.prm.productsale.dto.DataMailDTO;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.repository.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MailServicesImp implements MailServices {
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private SpringTemplateEngine templateEngine;
  @Autowired
  private UserRepo userRepo;
  // Map lưu mã xác thực tạm thời (nên có thời gian hết hạn khi triển khai thực tế)
  private Map<String, String> verificationCodes = new ConcurrentHashMap<>();

  @Override
  public void sendHtmlMail(DataMailDTO dataMailDTO, String templateName) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
    Context context = new Context();
    context.setVariables(dataMailDTO.getProps());
    String html = templateEngine.process(templateName, context);
    helper.setTo(dataMailDTO.getTo());
    helper.setSubject(dataMailDTO.getSubject());
    helper.setText(html, true);
    mailSender.send(message);
  }

  // Hàm tạo mã xác thực ngẫu nhiên
  private String generateVerificationCode() {
    // Ví dụ: mã gồm 6 chữ số
    int code = (int)(Math.random() * 900000) + 100000;
    return String.valueOf(code);
  }

  // Hàm gửi email mã xác thực
  public void sendVerificationEmail(String email) throws MessagingException {
    if(userRepo.existsByEmail(email)) throw new AppException(ErrorCode.USER_EXIST);

    // Tạo mã xác thực
    String code = generateVerificationCode();
    // Lưu mã xác thực theo email
    verificationCodes.put(email, code);

    // Chuẩn bị dữ liệu gửi mail
    DataMailDTO dataMailDTO = new DataMailDTO();
    dataMailDTO.setTo(email);
    dataMailDTO.setSubject("Mã xác thực của bạn");
    Map<String, Object> props = new HashMap<>();
    props.put("code", code); // truyền biến 'code' cho template
    dataMailDTO.setProps(props);

    // Gửi mail với template "verification" (file verification.html)
    sendHtmlMail(dataMailDTO, "verification");
  }

  // Hàm kiểm tra mã xác thực
  public boolean verifyCode(String email, String code) {
    String storedCode = verificationCodes.get(email);
    if (storedCode != null && storedCode.equals(code)) {
      // Nếu cần, xóa mã sau khi xác thực thành công để tránh lạm dụng
      verificationCodes.remove(email);
      return true;
    }
    return false;
  }
}
