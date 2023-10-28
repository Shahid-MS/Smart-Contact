package com.smart.contact.smartcontactmanager.dao;

import com.smart.contact.smartcontactmanager.Entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders, Long> {
  public Orders findByOrderId(String orderId);
}
