package com.smart.contact.smartcontactmanager.Controller;

import com.smart.contact.smartcontactmanager.Entities.User;
import com.smart.contact.smartcontactmanager.Helper.Message;
import com.smart.contact.smartcontactmanager.dao.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepo;

  @GetMapping("/")
  public String home(Model m) {
    m.addAttribute("title", "Home - Smart Contact");
    return "home";
  }

  @GetMapping("/signup")
  public String signup(Model m) {
    m.addAttribute("title", "SignUp - Smart Contact");
    m.addAttribute("user", new User());
    return "signup";
  }

  //Register user
  @PostMapping("/register")
  public String register(
    @Valid @ModelAttribute("user") User user,
    BindingResult res,
    @RequestParam(value = "agreement", defaultValue = "false") boolean agreed,
    Model model,
    HttpSession session
  ) {
    try {
      // System.out.println(agreed);
      // System.out.println(user);

      if (!agreed) {
        System.out.println("Not agreed Terms and conditions");
        throw new Exception("Not agreed Terms and conditions");
      }

      if (res.hasErrors()) {
        model.addAttribute("user", user);
        return "signup";
      }

      user.setRole("USER");
      user.setEnabled(true);
      user.setPassword(passwordEncoder.encode(user.getPassword()));

      userRepo.save(user);
      // System.out.println(result);
      model.addAttribute("user", new User());

      session.setAttribute(
        "message",
        new Message("Successfully Registered", "alert-success")
      );
      return "signup";
    } catch (Exception e) {
      e.printStackTrace();
      session.setAttribute(
        "message",
        new Message("Something went wrong" + e.getMessage(), "alert-danger")
      );
      return "signup";
    }
  }

  @GetMapping("/signin")
  public String logIn(Model m) {
    m.addAttribute("title", "LogIn - Smart Contact");
    return "signin";
  }
}
