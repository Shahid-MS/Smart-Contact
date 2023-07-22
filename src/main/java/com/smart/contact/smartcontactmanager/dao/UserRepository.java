package com.smart.contact.smartcontactmanager.dao;

import com.smart.contact.smartcontactmanager.Entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {
  @Query("select u from User u where u.email =:email")
  public User getUserByName(@Param("email") String email);


}
