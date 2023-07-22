package com.smart.contact.smartcontactmanager.dao;

import com.smart.contact.smartcontactmanager.Entities.Contact;
import com.smart.contact.smartcontactmanager.Entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface ContactRepository extends CrudRepository<User, Integer> {
  // @Query("from Contact as c where c.user.id =:userId")
  // public List<Contact> findAllContactsByUserName(@RequestParam("userId")int userId);

  //Pagination
  //current page
  //Contact per page
  @Query("from Contact as c where c.user.id =:userId")
  public Page<Contact> findAllContactsByUserName(
    @RequestParam("userId") int userId,
    Pageable pageable
  );


  @Query("from Contact as c where c.id=:id")
  public Contact findBycID(int id);


}
