package com.smart.contact.smartcontactmanager.Controller;

import com.smart.contact.smartcontactmanager.Entities.User;
import com.smart.contact.smartcontactmanager.Service.EmailService;
import com.smart.contact.smartcontactmanager.dao.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class forgotController {

  @Autowired
  private EmailService emailService;

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  Random rand = new Random(100000);

  @RequestMapping("/forgot")
  public String forgotFormVariefy() {
    return "forgotForm";
  }

  @PostMapping("/send-otp")
  public String sendOTP(
    @RequestParam("email") String email,
    HttpSession session
  ) {
    // System.out.println("Email : " + email);

    //generate otp of 6 digit
    int otp = 000000;
    for (int i = 0; i < 5; i++) {
      otp = rand.nextInt(900000) + 100000;
    }
    // System.out.println("otp : " + otp);

    //code to send mail
    String subject = "OTP verification from Smart Contact";
    String message = "OTP is " + otp;
    String to = email;
    boolean flag = this.emailService.sendEmail(subject, message, to);

    if (flag) {
      session.setAttribute("originalOtp", otp);
      session.setAttribute("email", email);
      return "/variefyOtp";
    } else {
      session.setAttribute("message", "Invalid Email");
      return "/forgotForm";
    }
  }

  @PostMapping("/verify-otp")
  public String verifyOTP(@RequestParam("otp") int otp, HttpSession session) {
    int originalOtp = (int) session.getAttribute("originalOtp");
    String email = (String) session.getAttribute("email");
    if (originalOtp == otp) {
      User user = userRepo.getUserByName(email);
      if (user == null) {
        session.setAttribute("message", "User does not exist");
        return "/forgotForm";
      }
      return "/changePassword";
    } else {
      session.setAttribute("message", "Wrong OTP");
      return "variefyOtp";
    }
  }

  @PostMapping("/change-password")
  public String changePassword(
    @RequestParam("password") String password,
    HttpSession session
  ) {
    String email = (String) session.getAttribute("email");
    User user = userRepo.getUserByName(email);
    user.setPassword(bCryptPasswordEncoder.encode(password));
    userRepo.save(user);

    return "redirect:/signin?change=Password changed successfully";
  }
}
