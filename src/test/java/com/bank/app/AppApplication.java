package com.bank.app;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.bank.app.controller.ClientController;

@WebMvcTest(ClientController.class)
@SpringBootTest
class AppApplication {
}
