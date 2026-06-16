package com.rajesh.Spring_AI_Demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api")
public class OpenAiController {

    private ChatClient chatClient;
    ChatMemory chatMemory= MessageWindowChatMemory.builder().build();

    // this is useful for multiple models to work with.
//    public OpenAiController(OpenAiChatModel chatModel){
//        this.chatClient= ChatClient.create(chatModel);
//    }

    //this is for One model usage
    public OpenAiController(ChatClient.Builder builder){
        this.chatClient=builder
                // for in memory we have to use this method defaultAdvisors().
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }
//rest api -> get
    @GetMapping("chat/{conversationId}/{message}") // open ai wants the conversationID just make it default
    public String getResponse(@PathVariable String message, @PathVariable String conversationId){
        // working with chatClient
//        String response=chatClient
//                .prompt(message)
//                .call()
//                .content();


        //In Memory chat model
        ChatResponse chatResponse=chatClient
                .prompt(message)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId))
                .call()
                .chatResponse();

        System.out.println(chatResponse.getMetadata().getModel());

        String response=chatResponse
                .getResult()
                .getOutput()
                .getText();

        return response;
    }

    @PostMapping("{conversationId}/recommend")
    public String recommend(@PathVariable String conversationId, @RequestParam String type, @RequestParam String year, @RequestParam String lang){

        String temp= """
                    I want to watch a {type} movie tonight with good rating,
                    looking for movies around this year {year},
                    The language i'm looking for is {lang},
                    Suggest one specific movie and tell the cast and length of the movie.
                """;

        PromptTemplate promptTemplate=new PromptTemplate(temp);
        Prompt prompt=promptTemplate.create(Map.of("type", type,"year",year,"lang",lang));

        String response = chatClient
                .prompt(prompt)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId))
                .call()
                .content();
        return response;
    }
}
