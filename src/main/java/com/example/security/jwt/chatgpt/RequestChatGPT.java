package com.example.security.jwt.chatgpt;

import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import lombok.Builder;

import java.util.List;

public record RequestChatGPT() {
    @Builder
    public record Question(
            List<MultiChatMessage> chatHistories,
            String prompt
    ) {

    }
    @Builder
    public record QuestionWithPersonality(
            String personality, // {직업: 목장 주인, 마을에서의 역할 : 촌장, 성별: 남성, 나이: 50대, 성격: 온화함, 특징: 이방인은 환영하며 목장주인으로서 여러 반려동물들과 함께 살고 있다., 상황: 상대방이 마을에 처음 오게됨. 대화 중 }
            List<MultiChatMessage> chatHistories,
            String prompt
    ) {

    }
}
