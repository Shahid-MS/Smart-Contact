package com.smart.contact.smartcontactmanager.Helper;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {

  public void removeMessageFromSession() {
    try {
    //   System.out.println("Inside remove attribute Session");
      HttpSession session =
        (
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()
        ).getRequest()
          .getSession();

      session.removeAttribute("message");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
