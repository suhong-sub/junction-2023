package com.example.security.jwt.chatgpt;

import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatgptService chatgptService;

    public List<MultiChatMessage> requestPersonalityChat(RequestChatGPT.QuestionWithPersonality request) {
        System.out.println(request);

        List<MultiChatMessage> chatHistories;
        if (request.chatHistories() == null) { // 방어로직
            chatHistories = new ArrayList<>();
        } else {
            chatHistories = new ArrayList<>(request.chatHistories());
        }

        if (chatHistories.size() == 0) {
            // 처음이라면 인격부여
            MultiChatMessage first = new MultiChatMessage();
            first.setRole("system"); // ~~ 스타일로 답변해줘 같은 역할을 할 때 system 사용
            first.setContent(getAct(request.personality()));
            chatHistories.add(first);
            String output = chatgptService.multiChat(chatHistories);
            //

            // 인격부여 후 응답을 배열에 추가
            MultiChatMessage outputMessage = new MultiChatMessage();
            outputMessage.setRole("assistant"); // 챗지피티 답변 롤
            outputMessage.setContent(output);
            chatHistories.add(outputMessage);
        }

        // 새로운 입력, 답변 삽입
        MultiChatMessage newMessage = new MultiChatMessage();
        newMessage.setRole("user"); // 유저 질문
        newMessage.setContent(request.prompt());

        chatHistories.add(newMessage);
        String output = chatgptService.multiChat(chatHistories);
        MultiChatMessage newOutput = new MultiChatMessage();
        newOutput.setRole("assistant");
        newOutput.setContent(output);
        chatHistories.add(newOutput);
        return chatHistories;
    }

    // system role로 인격부여 (최초 1번)
    private String getAct(String personality) {
        String personalityInput = "Act as this Character[" + personality + "]\n\n";
        String defaultSettingInput = "Say less than 10 words. " +
                "Don't break character. " +
                "don't ever mention that you are an AI model. " +
                "Please refer to your partner's previous said. " +
                "\n\n";

        String input = personalityInput + defaultSettingInput;
        return input;
    }
}
