package com.smart.contact.smartcontactmanager.Config;

import com.smart.contact.smartcontactmanager.Entities.User;
import com.smart.contact.smartcontactmanager.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class customUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {

    //Fetch user from Database
    User user = userRepo.getUserByName(username);
    if (user == null) {
      throw new UsernameNotFoundException("Could not found user");
    }

    customUserDetails customDetails = new customUserDetails(user);
    return customDetails;
  }
}
