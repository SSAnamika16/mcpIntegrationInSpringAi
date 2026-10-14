//
//
//package com.mcp.app.mcp_app.controller;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
//import org.springframework.ai.tool.ToolCallbackProvider;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/ai")
//public class AiController {
//
//    private final ChatClient chatClient;
//
//    public AiController(
//            ChatClient.Builder chatClientBuilder,
//            ToolCallbackProvider toolCallbackProvider) {
//
//        this.chatClient = chatClientBuilder
//                .defaultSystem("""
//                    You are an AI assistant with access to MCP filesystem tools.
//
//                    When the user explicitly requests creating or updating a
//                    file, use the appropriate filesystem tool, such as
//                    write_file, to perform the requested operation.
//
//                    For java.md, use this absolute path:
//                    C:/Users/ANAMIKA/Desktop/springAiOnly/mcp-app/mcp/java.md
//
//                    Do not merely display the file contents in your response.
//                    Actually call the filesystem tool to create or update
//                    the file, then report the result based on the tool output.
//
//                    If the tool call fails, explain the actual error.
//                    Do not claim that a file was created unless the tool
//                    confirms the operation.
//                    """)
//                .defaultTools(toolCallbackProvider)
//                .defaultAdvisors(new SimpleLoggerAdvisor())
//                .build();
//    }
//
//    @PostMapping("/chat")
//    public ResponseEntity<String> getAiResponse(
//            @RequestParam("query") String query) {
//
//        String response = chatClient
//                .prompt()
//                .user(query)
//                .call()
//                .content();
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/groq")
//    public ResponseEntity<String> getResponseFromGroq(
//            @RequestParam("query") String query) {
//
//        String response = chatClient
//                .prompt()
//                .user(query)
//                .call()
//                .content();
//
//        return ResponseEntity.ok(response);
//    }
//}






















package com.mcp.app.mcp_app.controller;


import okhttp3.Response;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {


    private ChatClient chatClient;

    public AiController(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = chatClientBuilder
//                .defaultSystem("You are an assistant that can use the connected MCP filesystem server.")
                .defaultTools(toolCallbackProvider)
                .defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }

//    localhost:8989/ai/chat?query=tell about java

    @PostMapping("/chat")
    public ResponseEntity<String> getAiResponse(
            @RequestParam("query") String query
    ) {
        String response = chatClient
                .prompt(query)
                .call()
                .content();

       return ResponseEntity.ok(response);
    }


//    http://localhost:8000/ai/groq?query=hello

    @PostMapping("/groq")
    public ResponseEntity<String> getResponseFromGroq(
            @RequestParam("query") String query
    ) {

        return ResponseEntity.ok(chatClient.prompt(query).call().content());

    }



}
