package com.epam.training.gen.ai.controller;

import com.epam.training.gen.ai.application.HomeAssistantFacade;
import com.epam.training.gen.ai.controller.dto.PromptRequest;
import com.epam.training.gen.ai.controller.dto.PromptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class HomeAssistantController {
    private final HomeAssistantFacade homeAssistantFacade;

    @PostMapping("/home-assistant")
    public PromptResponse processRequest(@RequestBody PromptRequest request) {
        return new PromptResponse(homeAssistantFacade.processRequest(request));
    }
}
