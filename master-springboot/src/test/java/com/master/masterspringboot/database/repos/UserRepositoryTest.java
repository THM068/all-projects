package com.master.masterspringboot.database.repos;

import com.master.masterspringboot.database.entity.User;
import com.master.masterspringboot.database.entity.repos.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource("classpath:config.test.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Environment env;

    @Test
    void createAuser() {
        var user = new User("thando.mafela@hotmail.com","test1234", true );

        assertNotNull(userRepository.save(user));
        Optional<User> optionalResult = userRepository.findById("thando.mafela@hotmail.com");

        assertTrue(optionalResult.isPresent());
        User result = optionalResult.get();
        assertEquals("thando.mafela@hotmail.com", result.getUsername());
        assertEquals("test1234", result.getPassword());
        assertEquals(true, result.getEnabled().booleanValue());

    }

    @Test
    void canRetrieveTestConfig() {
        assertEquals("Test.user", env.getProperty("user.name"));
        assertEquals("Test.password", env.getProperty("user.password"));
    }
}
