package com.example.security.jwt.tamagotchi;

import com.example.security.jwt.account.domain.Account;
import com.example.security.jwt.account.repository.AccountRepository;
import com.example.security.jwt.chatgpt.ChatService;
import com.example.security.jwt.chatgpt.RequestChatGPT;
import com.example.security.jwt.global.exception.ErrorCode;
import com.example.security.jwt.global.exception.error.CommonException;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TamagotchiService {
    private final ChatService chatService;
    private final TamagotchiRepository tamagotchiRepository;
    private final AccountRepository accountRepository;

    public void post(String username, RequestTamagotchi.Register registerDto) {
        Account account = accountRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "-> 찾을 수 없습니다"));

        Tamagotchi tamagotchi = Tamagotchi.builder()
                .account(account)
                .name(registerDto.name())
                .personality(registerDto.personality())
                .type(registerDto.type())
                .build();

        tamagotchiRepository.save(tamagotchi);
    }

    public ResponseTamagotchi.Info getMyTamagotchis(String username) {
        Account account = accountRepository.findOneWithAuthoritiesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "-> 찾을 수 없습니다"));

        List<Tamagotchi> tamagotchis = tamagotchiRepository.findAllByAccount(account);
        return ResponseTamagotchi.Info.builder()
                .tamagotchis(tamagotchis)
                .build();
    }

    public void patch(Long tamagotchiId, RequestTamagotchi.PatchType patchType) {
        Tamagotchi tamagotchi = tamagotchiRepository.findById(tamagotchiId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TAMAGOTCHI));

        tamagotchi.updateType(patchType.type());
    }

    public List<MultiChatMessage> chat(Long tamagotchiId, RequestChatGPT.Question question) {
        Tamagotchi tamagotchi = tamagotchiRepository.findById(tamagotchiId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TAMAGOTCHI));

        RequestChatGPT.QuestionWithPersonality questionWithPersonality = RequestChatGPT.QuestionWithPersonality.builder()
                .personality(tamagotchi.getPersonality())
                .chatHistories(question.chatHistories())
                .prompt(question.prompt())
                .build();
        return chatService.requestPersonalityChat(questionWithPersonality);
    }

    public void delete(Long tamagotchiId) {
        Tamagotchi tamagotchi = tamagotchiRepository.findById(tamagotchiId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TAMAGOTCHI));

        tamagotchiRepository.delete(tamagotchi);
    }

    public void put(Long tamagotchiId, RequestTamagotchi.Register registerDto) {
        Tamagotchi tamagotchi = tamagotchiRepository.findById(tamagotchiId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TAMAGOTCHI));

        tamagotchi.updateName(registerDto.name());
        tamagotchi.updatePersonality(registerDto.personality());
    }

    public void increaseIntimacy(Long tamagotchiId) {
        Tamagotchi tamagotchi = tamagotchiRepository.findById(tamagotchiId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TAMAGOTCHI));

        tamagotchi.increaseIntimacy();
    }

    public List<ResponseTamagotchi.Info> getIntimacyRanking() {
        List<Tamagotchi> fetch = tamagotchiRepository.findAllByOrderByIntimacyRateDesc();
        List<ResponseTamagotchi.Info> rankingList = fetch.stream()
                .map(t -> ResponseTamagotchi.Info.builder()
                        .tamagotchis(fetch)
                        .build())
                .toList();

        return rankingList;
    }

}
