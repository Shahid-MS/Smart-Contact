package com.smart.contact.smartcontactmanager.Controller;

import com.smart.contact.smartcontactmanager.Entities.Contact;
import com.smart.contact.smartcontactmanager.Entities.User;
import com.smart.contact.smartcontactmanager.dao.ContactRepository;
import com.smart.contact.smartcontactmanager.dao.UserRepository;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class searchController {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private ContactRepository contactRepo;

  @GetMapping("/search/{query}")
  public ResponseEntity<?> search(
    @PathVariable("query") String query,
    Principal p
  ) {
    // System.out.println(query);
    User user = this.userRepo.getUserByName(p.getName());
    List<Contact> contacts =
      this.contactRepo.findByNameAndUser(query, user);
    return ResponseEntity.ok(contacts);
  }
}
