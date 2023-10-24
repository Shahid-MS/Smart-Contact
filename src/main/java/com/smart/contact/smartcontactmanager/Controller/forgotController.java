package com.smart.contact.smartcontactmanager.Controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.contact.smartcontactmanager.Service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class forgotController {
    @Autowired
    private EmailService emailService;
  Random rand = new Random(100000);

  @RequestMapping("/forgot")
  public String forgotFormVariefy() {
    return "forgotForm";
  }

  @PostMapping("/send-otp")
  public String sendOTP(@RequestParam("email") String email , HttpSession session)   {
    // System.out.println("Email : " + email);

    //generate otp of 6 digit
    int otp = rand.nextInt(999999);
    // System.out.println("otp : " + otp);

    //code to send mail
    String subject = "OTP verification from Smart Contact";
    String message = "OTP is "+otp;
    String to = email;
    boolean flag = this.emailService.sendEmail(subject, message, to);

    if (flag) {
        return "/variefyOtp";
    }

    else{
        session.setAttribute("message", "Invalid Email");
        return "/forgotForm";
    }
    
  }
}
