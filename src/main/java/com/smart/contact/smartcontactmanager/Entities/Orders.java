package com.smart.contact.smartcontactmanager.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long myOrderId;

  private String orderId;

  private String amount;
  private String reciept;
  private String status;

  @ManyToOne
  private User user;

  private String paymentId;

  public Orders() {}

  public Orders(
    Long myOrderId,
    String orderId,
    String amount,
    String reciept,
    String status,
    User user,
    String paymentId
  ) {
    this.myOrderId = myOrderId;
    this.orderId = orderId;
    this.amount = amount;
    this.reciept = reciept;
    this.status = status;
    this.user = user;
    this.paymentId = paymentId;
  }

  public Long getMyOrderId() {
    return myOrderId;
  }

  public void setMyOrderId(Long myOrderId) {
    this.myOrderId = myOrderId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getReciept() {
    return reciept;
  }

  public void setReciept(String reciept) {
    this.reciept = reciept;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  @Override
  public String toString() {
    return (
      "Orders [myOrderId=" +
      myOrderId +
      ", orderId=" +
      orderId +
      ", amount=" +
      amount +
      ", reciept=" +
      reciept +
      ", status=" +
      status +
      ", user=" +
      user +
      ", paymentId=" +
      paymentId +
      "]"
    );
  }
}
