package com.smart.contact.smartcontactmanager.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String name;
  private String work;
  private String email;
  private String phone;
  private String image;

  @Column(length = 1000)
  private String description;

  @ManyToOne
  private User user;

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

  public String getWork() {
    return work;
  }

  public void setWork(String work) {
    this.work = work;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return (
      "Contact [id=" +
      id +
      ", name=" +
      name +
      ", work=" +
      work +
      ", email=" +
      email +
      ", phone=" +
      phone +
      ", image=" +
      image +
      ", description=" +
      description +
      ", user=" +
      user +
      "]"
    );
  }

  @Override
  public boolean equals(Object obj) {
    return this.id == ((Contact) obj).getId();
  }
}