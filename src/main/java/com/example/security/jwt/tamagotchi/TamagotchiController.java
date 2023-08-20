package com.example.security.jwt.tamagotchi;

import com.example.security.jwt.chatgpt.RequestChatGPT;
import com.example.security.jwt.global.dto.CommonResponse;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TamagotchiController {
    private final TamagotchiService tamagotchiService;

    // 타마고치 생성
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/api/v1/tamagotchis")
    public ResponseEntity<CommonResponse> postMyTamagotchi(@AuthenticationPrincipal User user, @RequestBody RequestTamagotchi.Register registerDto) {
        tamagotchiService.post(user.getUsername(), registerDto);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // 내 타마고치 조회
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('MEMBER')")
    @GetMapping("/api/v1/tamagotchis/my")
    public ResponseEntity<CommonResponse> getMy(@AuthenticationPrincipal User user) {
        ResponseTamagotchi.Info info = tamagotchiService.getMyTamagotchis(user.getUsername());

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(info)
                .build();
        return ResponseEntity.ok(response);
    }

    // 타마고치에게 말걸기
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/api/v1/tamagotchis/{id}/chat")
    public ResponseEntity<CommonResponse> postChat(@PathVariable Long id, @RequestBody RequestChatGPT.Question question) {
        List<MultiChatMessage> chatMessages = tamagotchiService.chat(id, question);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(chatMessages)
                .build();
        return ResponseEntity.ok(response);
    }

    // 타마고치 삭제
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('MEMBER')")
    @DeleteMapping("/api/v1/tamagotchis/{id}")
    public ResponseEntity<CommonResponse> deleteChat(@PathVariable Long id) {
        tamagotchiService.delete(id);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // 타마고치 변경(갱신)
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('MEMBER')")
    @PutMapping("/api/v1/tamagotchis/{id}")
    public ResponseEntity<CommonResponse> putTamagotchi(@PathVariable Long id, @RequestBody RequestTamagotchi.Register registerDto) {
        tamagotchiService.put(id, registerDto);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/api/v1/tamagotchis/{id}/type")
    public ResponseEntity<CommonResponse> patchTamagotchi(@PathVariable Long id, @RequestBody RequestTamagotchi.PatchType patch) {
        tamagotchiService.patch(id, patch);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // 타마고치 친밀도 업
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/api/v1/tamagotchis/{id}/increase-intimacy")
    public ResponseEntity<CommonResponse> patchIntimacyRate(@PathVariable Long id) {
        tamagotchiService.increaseIntimacy(id);

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    // 랭킹조회
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('MEMBER')")
    @GetMapping("/api/v1/tamagotchis/ranking")
    public ResponseEntity<CommonResponse> getRanking() {
        List<ResponseTamagotchi.Info> rankingList = tamagotchiService.getIntimacyRanking();

        CommonResponse response = CommonResponse.builder()
                .success(true)
                .response(rankingList)
                .build();
        return ResponseEntity.ok(response);
    }
}
