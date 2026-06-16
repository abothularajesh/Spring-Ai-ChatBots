package com.rajesh.Spring_AI_Ollama;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {

    private final ChatClient chatClient;

    public OllamaService(ChatModel chatmodel){
        this.chatClient=ChatClient.create(chatmodel);
    }


    public String getResponse(String message) {
        ChatResponse chatResponse=chatClient
                .prompt(message)
                .call()
                .chatResponse();

        System.out.println(chatResponse.getMetadata().getModel());

        String response=chatResponse
                .getResult()
                .getOutput()
                .getText();
        return response;
    }
}
