package com.ht.managemybills;
import com.ht.managemybills.config.SocialMediaAccount;
import com.ht.managemybills.config.SocialMediaProperties;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SocialMediaPropertiesTest {
 @Autowired
 SocialMediaProperties socialMediaProperties;

 @Test
  void shouldHaveProperties() {
  final List<SocialMediaAccount> accounts = socialMediaProperties.accounts();
  Assertions.assertEquals(2, accounts.size());

 }
}
