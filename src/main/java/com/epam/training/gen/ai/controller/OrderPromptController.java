package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.controller.dto.PromptRequest;
import com.epam.training.gen.ai.controller.dto.PromptResponse;
import com.epam.training.gen.ai.controller.dto.PromptResponseWithHistory;
import com.epam.training.gen.ai.domain.OrderPromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OrderPromptController {
    private final OrderPromptService promptService;

    @PostMapping("/prompt")
    public PromptResponse processPrompt(@RequestBody PromptRequest request) {
        var promptCompletions = promptService.getPromptCompletions(request.input());
        return new PromptResponse(promptCompletions.stream().findFirst().orElse(""));
    }

    @PostMapping("/order-pizza")
    public PromptResponseWithHistory processOrder(@RequestBody PromptRequest request) {
        var promptCompletions = promptService.getPromptCompletionsWithHistory(request.input());
        return new PromptResponseWithHistory(promptCompletions);
    }
}
