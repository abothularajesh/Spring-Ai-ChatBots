package com.rajesh.Spring_AI_Gemini;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    public GeminiController(GeminiService geminiService){
        this.geminiService=geminiService;
    }

    @GetMapping("chat/{conversationId}/{message}")
    public String getResponse(@PathVariable String message,@PathVariable String conversationId){
        return geminiService.generateResponse(message,conversationId);
    }
}
