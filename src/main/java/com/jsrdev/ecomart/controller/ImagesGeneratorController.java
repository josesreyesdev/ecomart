package com.jsrdev.ecomart.controller;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImagesGeneratorController {

    private final ImageModel imageModel;

    public ImagesGeneratorController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping()
    String imageGenerator(String prompt) {
        var options = ImageOptionsBuilder.builder()
                .withHeight(1024)
                .withHeight(1924)
                .build();
        var response = imageModel.call(new ImagePrompt(prompt, options));

         return response.getResult().getOutput().getUrl();
    }
}
