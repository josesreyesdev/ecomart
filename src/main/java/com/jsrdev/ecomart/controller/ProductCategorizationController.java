package com.jsrdev.ecomart.controller;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/categorization")
public class ProductCategorizationController {

    private final ChatClient chatClient;

    public ProductCategorizationController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptionsBuilder
                        .builder()
                        .withModel("gpt-4o-mini").build())
                .build();
    }

    @GetMapping()
    String productCategorization(String product) {
        //var product = "Genera 5 productos ecológicos";
        var system = """
                        Actúa como un categorizador de productos y debes responder solo el nombre de la categoría del producto informado
                
                        Escoge una categoría de la siguiente lista:
                
                        1. Higiene Personal
                        2. Electrónicos
                        3. Deportes
                        4 Otros
                
                        Ejemplo de uso:
                
                        Producto: Pelota de fútbol
                        Respuesta: Deportes
                """;

        var tokens = counterTokens(system, product);
        System.out.println("Tokens: "+ tokens);

        return this.chatClient.prompt()
                .system(system)
                .user(product)
                .options(ChatOptionsBuilder.builder()
                        .withTemperature(0.82)
                        .build()
                )
                .call()
                .content();
    }

    private int counterTokens(String system, String user) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncodingForModel(ModelType.GPT_4O_MINI);

        return enc.countTokens(system + user);
    }


}
