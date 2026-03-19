package com.example.MyNewProject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private  ChatClient chatClient;
    public AiService(ChatClient.Builder builder){
        this.chatClient =builder.build();
    }


    public String generateSummary(String propt) {
        String response="";
        try{
             response = chatClient.prompt(propt).call().content();
        } catch (Exception e) {
            throw new RuntimeException("Getting Error in Fetching details with help of ai");
        }
        return response;
    }
}
