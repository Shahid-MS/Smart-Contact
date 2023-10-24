package com.smart.contact.smartcontactmanager.Controller;

// import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.smart.contact.smartcontactmanager.Entities.Contact;
import com.smart.contact.smartcontactmanager.Entities.User;
import com.smart.contact.smartcontactmanager.Helper.Message;
import com.smart.contact.smartcontactmanager.dao.ContactRepository;
import com.smart.contact.smartcontactmanager.dao.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
// import java.util.List;
// import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private UserRepository userRepo;

  @Autowired
  private ContactRepository contactRepo;

  @ModelAttribute
  public void addCommonData(Model m, Principal principal) {
    User user = userRepo.getUserByName(principal.getName());
    m.addAttribute("user", user);
  }

  @GetMapping("/dashboard")
  public String dashBoard(Model m, Principal principal) {
    m.addAttribute("title", "Dashboard - Smart Contact");
    // String username = principal.getName();
    // System.out.println(username);

    // User user = userRepo.getUserByName(principal.getName());
    // System.out.println(user);

    // m.addAttribute("user", user);

    return "User/dashboard";
  }

  @GetMapping("/add-contactForm")
  public String addContact(Model m) {
    m.addAttribute("title", "Add new Contact - Smart Contact");
    m.addAttribute("contact", new Contact());
    return "User/addContact";
  }

  @PostMapping("/process-contact")
  public String processContact(
    @ModelAttribute("contact") Contact contact,
    Principal principal,
    @RequestParam("profileImage") MultipartFile file,
    HttpSession session
  ) {
    try {
      // System.out.println(contact);

      String name = principal.getName();
      User user = userRepo.getUserByName(name);

      //Processing and uploading file
      if (file.isEmpty()) {
        // System.out.println("File is Empty");
        contact.setImage("contact.png");
      } else {
        //upload file and adding to contact image
        contact.setImage(file.getOriginalFilename());
        File saveFile = new ClassPathResource("static/Images").getFile();

        Path path = Paths.get(
          saveFile.getAbsolutePath() +
          File.separator +
          file.getOriginalFilename()
        );
        Files.copy(
          file.getInputStream(),
          path,
          StandardCopyOption.REPLACE_EXISTING
        );
        // System.out.println("image is uploaded");
      }

      contact.setUser(user);
      user.getContacts().add(contact);

      //Throwing exception
      // if (3 > 2) {
      //   throw new Exception();
      // }

      userRepo.save(user);

      //Adding successfull message
      session.setAttribute(
        "message",
        new Message("Successfully Added", "success")
      );
      // System.out.println("Contact Saved");

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Something went wrong" + e.getMessage());
      session.setAttribute(
        "message",
        new Message("Something went wrong", "danger")
      );
    }
    return "User/addContact";
  }

  //Show Contacts
  @GetMapping("/show-contacts/{page}")
  public String showContact(
    @PathVariable("page") Integer page,
    Model m,
    Principal principal
  ) {
    m.addAttribute("title", "Show Contacts - Smart Contact");

    //List of contacts to be sent
    User user = userRepo.getUserByName(principal.getName());

    //Not use create contact Repository
    // List<Contact> contacts = user.getContacts();

    // List<Contact> contacts = contactRepo.findAllContactsByUserName(user.getId());

    // m.addAttribute("contacts" , contacts);

    // Pagination

    // System.out.println(page);
    // System.out.println("Inside page");

    Pageable pageable = PageRequest.of(page, 5);

    Page<Contact> contacts = contactRepo.findAllContactsByUserName(
      user.getId(),
      pageable
    );
    // System.out.println(contacts);

    m.addAttribute("contacts", contacts);
    m.addAttribute("currentPage", page);
    m.addAttribute("totalPages", contacts.getTotalPages());

    return "User/showContact";
  }

  //Specific contact

  @GetMapping("/contact/{cId}")
  public String parContact(
    @PathVariable("cId") Integer cId,
    Model m,
    Principal p
  ) {
    // System.out.println("cid : " + cId);

    Contact contact = contactRepo.findBycID(cId);
    // System.out.println(contact.getName());

    //contact user same to acccess
    User user = userRepo.getUserByName(p.getName());
    if (contact.getUser().getId() == user.getId()) {
      m.addAttribute("contact", contact);
    }
    m.addAttribute("title", contact.getName() + " - SCMSP");

    return "User/parContact";
  }

  //delete Handler

  @GetMapping("/delete/{cId}")
  public String deleteContact(
    @PathVariable("cId") Integer cId,
    Model m,
    Principal p,
    HttpSession session
  ) throws IOException {
    // System.out.println(cId);
    Contact contact = contactRepo.findBycID(cId);
    User user = userRepo.getUserByName(p.getName());

    if (user.getId() == contact.getUser().getId()) {
      contact.setUser(null);

      //remove pic from images
      if (!contact.getImage().equals("contact.png")) {
        File deleteFile = new ClassPathResource("static/Images").getFile();
        File file1 = new File(deleteFile, contact.getImage());
        file1.delete();
      }

      user.getContacts().remove(contact);
      userRepo.save(user);

      session.setAttribute(
        "message",
        new Message("successfully Deleted", "success")
      );
    } else {
      session.setAttribute(
        "message",
        new Message("Something went wrong", "danger")
      );
    }
    return "redirect:/user/show-contacts/0";
  }

  //Update form
  @PostMapping("/contact/update-contact/{cId}")
  public String updateContact(@PathVariable("cId") Integer cId, Model m) {
    m.addAttribute("title", "update - contact/" + cId);

    //Contact
    Contact contact = contactRepo.findBycID(cId);
    // Contact contact = contactRepo.findById(cId).get();
    m.addAttribute("contact", contact);

    return "User/updateContact";
  }

  @PostMapping("/contact/update-contact-process")
  public String contactUpdateProcess(
    @ModelAttribute Contact contact,
    @RequestParam("profileImage") MultipartFile file,
    Model m,
    HttpSession session,
    Principal p
  ) {
    try {
      Contact oldContact = contactRepo.findBycID(contact.getId());
      //Update pic
      if (!file.isEmpty()) {
        // System.out.println("Empty");

        // delete old pic
        if (!oldContact.getImage().equals("contact.png")) {
          File deleteFile = new ClassPathResource("static/Images").getFile();
          File file1 = new File(deleteFile, oldContact.getImage());
          file1.delete();
        }

        // upadte new pic
        File saveFile = new ClassPathResource("static/Images").getFile();
        Path path = Paths.get(
          saveFile.getAbsolutePath() +
          File.separator +
          file.getOriginalFilename()
        );

        Files.copy(
          file.getInputStream(),
          path,
          StandardCopyOption.REPLACE_EXISTING
        );

        contact.setImage(file.getOriginalFilename());
      } else {
        contact.setImage(oldContact.getImage());
      }

      User user = userRepo.getUserByName(p.getName());
      oldContact.setUser(null);
      user.getContacts().remove(oldContact);
      contact.setUser(user);
      user.getContacts().add(contact);
      userRepo.save(user);

      session.setAttribute(
        "message",
        new Message("Successfully Updated", "success")
      );
    } catch (Exception e) {
      e.printStackTrace();
      session.setAttribute(
        "message",
        new Message("Something went wrong", "danger")
      );
    }
    return "redirect:/user/contact/" + contact.getId();
  }

  @GetMapping("/your-profile")
  public String yourprofile(Model m , Principal p){

    User user = userRepo.getUserByName(p.getName());
    m.addAttribute("title", user.getName()+" Profile - SCMSP");

    return "User/yourProfile";
  }

  @GetMapping("/setting")
  public String setting(){
    return "User/setting";
  }
  
  @PostMapping("/change-password")
  public String changePassword(@RequestParam("oldPassword") String oldPassword , @RequestParam("newPassword") String newPassword, Principal p, HttpSession session){
    // System.out.println("old password "+oldPassword);
    // System.out.println("new password "+ newPassword);
    String userName = p.getName();
    User user = this.userRepo.getUserByName(userName);
    // System.out.println("password : "+ user.getPassword());

    if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
      System.out.println("Correct password");
      //change password
      user.setPassword(bCryptPasswordEncoder.encode(newPassword));
      userRepo.save(user);
      session.setAttribute("message", new Message("Your password is successfully saved", "success"));
    }
    else{
       session.setAttribute("message", new Message("Wrong Password !!", "danger"));
       return "redirect:/user/setting";
    }
    return "redirect:/user/dashboard";
  }
}
