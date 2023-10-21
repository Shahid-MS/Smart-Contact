package com.smart.contact.smartcontactmanager.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyConfig {

  @Bean
  public UserDetailsService getUserDetailsService() {
    return new customUserDetailsService();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // @Bean
  // public DaoAuthenticationProvider authenticationProvider() {
  //   DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
  //   daoAuthenticationProvider.setUserDetailsService(
  //     this.getUserDetailsService()
  //   );
  //   daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
  //   return daoAuthenticationProvider;
  // }


  //Using In memory
  // @Bean
  // public UserDetailsService userDetailsService() {
  //   UserDetails normalUser = User
  //     .withUsername("MS")
  //     .password(passwordEncoder().encode("MS"))
  //     .roles("USER")
  //     .build();

  //   UserDetails adminUser = User
  //     .withUsername("PS")
  //     .password(passwordEncoder().encode("PS"))
  //     .roles("ADMIN")
  //     .build();

  //   InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(
  //     normalUser,
  //     adminUser
  //   );
  //   return inMemoryUserDetailsManager;
  // }



  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity)
    throws Exception {
    httpSecurity
      .csrf()
      .disable()
      .authorizeHttpRequests(authorize ->
        authorize
          .requestMatchers("/user/**")
          .hasAuthority("USER")
          .requestMatchers("/**")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .formLogin().loginPage("/signin").loginProcessingUrl("/do_signin").defaultSuccessUrl("/user/dashboard");

    return httpSecurity.build();
  }
}
