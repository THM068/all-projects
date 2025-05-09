package com.ht.managemybills;

import com.ht.managemybills.config.SocialMediaProperties;
import com.ht.managemybills.entities.Post;
import com.ht.managemybills.entities.User;
import com.ht.managemybills.repositories.PostRepository;
import com.ht.managemybills.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(SocialMediaProperties.class)
public class ManagemybillsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ManagemybillsApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(PostRepository postRepository,
      UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return  args -> {
      var user = new User("user", passwordEncoder.encode("password"), "ROLE_ADMIN" );
      var user1 = new User("username", passwordEncoder.encode("password"), "ROLE_ADMIN" );
      var post = new Post("First Post", "This is the first post");
      postRepository.save(post);

      userRepository.save(user);
      userRepository.save(user1);
    };
  }

}
