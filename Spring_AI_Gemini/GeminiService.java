package com.rajesh.Spring_AI_Gemini;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    ChatMemory chatMemory= MessageWindowChatMemory.builder().build();

    private final ChatClient chatClient;
    public GeminiService(ChatClient.Builder builder){
        this.chatClient=builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public String generateResponse(String message, String conversationId) {
        ChatResponse chatResponse=chatClient
                .prompt(message)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId))
                .call()
                .chatResponse();

        System.out.println("Model Name : " +chatResponse.getMetadata().getModel());

         String response=chatResponse
                 .getResult()
                 .getOutput()
                 .getText();

        return response;
    }
}
