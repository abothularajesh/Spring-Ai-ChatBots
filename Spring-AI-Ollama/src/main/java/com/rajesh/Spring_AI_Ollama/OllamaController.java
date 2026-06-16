package com.rajesh.Spring_AI_Ollama;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class OllamaController {

    @Autowired
    private final OllamaService ollamaService;

    // this is useful for multiple models to work with.
    public OllamaController(OllamaService ollamaService){
        this.ollamaService=ollamaService;
    }

    @GetMapping("chat/{message}")
    public String getResponse(@PathVariable String message){
        return ollamaService.getResponse(message);
    }
}
