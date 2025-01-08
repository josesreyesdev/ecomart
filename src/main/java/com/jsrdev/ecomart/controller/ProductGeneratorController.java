package com.jsrdev.ecomart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/generator")
public class ProductGeneratorController {

    private final ChatClient chatClient;

    public ProductGeneratorController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping()
    String productGenerator() {
        var question = "Genera 5 productos ecológicos";
        return this.chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
