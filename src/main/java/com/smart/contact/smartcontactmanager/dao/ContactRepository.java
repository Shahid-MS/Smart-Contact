package com.smart.contact.smartcontactmanager.dao;

import com.smart.contact.smartcontactmanager.Entities.Contact;
import com.smart.contact.smartcontactmanager.Entities.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  //search
  // public List<Contact> findByNameContainingAndUser(String keywords, User user);

  //  @Query("SELECT c FROM Contact c WHERE c.name = :name AND c.user = :user")
  @Query("SELECT c FROM Contact c WHERE c.name LIKE %:name% AND c.user = :user")
  List<Contact> findByNameAndUser(
    @Param("name") String name,
    @Param("user") User user
  );
}
