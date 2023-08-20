package com.example.security.jwt.chatgpt;

import com.example.security.jwt.global.dto.CommonResponse;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatGPTController {
    private final ChatService chatService;

    @PostMapping("/api/v1/chatgpt/question-personality")
    public ResponseEntity<CommonResponse> request(@RequestBody RequestChatGPT.QuestionWithPersonality request) {
        List<MultiChatMessage> chatMessages = chatService.requestPersonalityChat(request);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(chatMessages)
                .build();
        return ResponseEntity.ok(response);
    }
}
