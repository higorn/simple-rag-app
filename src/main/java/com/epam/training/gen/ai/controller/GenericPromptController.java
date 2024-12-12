package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.controller.dto.PromptRequest;
import com.epam.training.gen.ai.controller.dto.PromptResponse;
import com.epam.training.gen.ai.domain.GenericPromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class GenericPromptController {
    private final GenericPromptService promptService;

    @PostMapping("/prompt")
    public PromptResponse processPrompt(@RequestBody PromptRequest request) {
        return new PromptResponse(promptService.getResponse(request));
    }
}
