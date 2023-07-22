package com.smart.contact.smartcontactmanager.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @NotBlank(message = "Can't be blank")
  @Size(min = 3, max = 20, message = "Name must be 3 - 20 Characters")
  private String name;

  @Column(unique = true)
  @Pattern(
    regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",
    message = "Not a valid Email"
  )
  private String email;

  private String password;
  private String role;
  private boolean enabled;
  private String imageURL;

  @Column(length = 100)
  private String about;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
  private List<Contact> contacts = new ArrayList<>();

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }

  @Override
  public String toString() {
    return (
      "User [id=" +
      id +
      ", name=" +
      name +
      ", email=" +
      email +
      ", password=" +
      password +
      ", role=" +
      role +
      ", enabled=" +
      enabled +
      ", imageURL=" +
      imageURL +
      ", about=" +
      about +
      ", contacts=" +
      contacts +
      "]"
    );
  }
}
