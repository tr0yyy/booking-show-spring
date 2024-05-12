package com.fmi.bookingshow.unit.controller;

import com.fmi.bookingshow.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("H2-Testing")
public class UserControllerTest {

    @Test
    public void testConstructor() {
        UserController userController = new UserController();
        assertNotNull(userController);
    }
}
