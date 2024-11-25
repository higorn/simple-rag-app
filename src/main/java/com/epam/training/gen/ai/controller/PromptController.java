package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.controller.dto.PromptRequest;
import com.epam.training.gen.ai.controller.dto.PromptResponse;
import com.epam.training.gen.ai.domain.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/prompt")
public class PromptController {
    private final PromptService promptService;

    @PostMapping
    public PromptResponse processPrompt(@RequestBody PromptRequest request) {
        var promptCompletions = promptService.getPromptCompletions(request.input());
        return new PromptResponse(promptCompletions.stream().findFirst().get());
    }
}