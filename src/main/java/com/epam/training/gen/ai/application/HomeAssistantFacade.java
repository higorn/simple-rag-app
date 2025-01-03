package com.epam.training.gen.ai.application;

import com.epam.training.gen.ai.controller.dto.PromptRequest;
import com.epam.training.gen.ai.domain.Light;
import com.google.gson.Gson;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.contextvariables.ContextVariableTypeConverter;
import com.microsoft.semantickernel.contextvariables.ContextVariableTypes;
import com.microsoft.semantickernel.orchestration.FunctionResult;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.epam.training.gen.ai.domain.KernelHelper.getChat;
import static com.epam.training.gen.ai.domain.KernelHelper.getKernelFunctionArguments;

@Slf4j
@Component
@RequiredArgsConstructor
public class HomeAssistantFacade {
    private final Kernel kernel;
    private final ChatHistory chatHistory;
    private final InvocationContext invocationContext;

    @PostConstruct
    public void init() {
        chatHistory.addSystemMessage("You are a Home Assistant");
        ContextVariableTypes
                .addGlobalConverter(
                        ContextVariableTypeConverter.builder(Light.class)
                                .toPromptString(new Gson()::toJson)
                                .build());
        log.info("Home Assistant initialized");
    }

    public String processRequest(PromptRequest request) {
        FunctionResult<String> response = kernel.invokeAsync(getChat())
                .withArguments(getKernelFunctionArguments(request.input(), chatHistory))
                .withInvocationContext(invocationContext)
                .block();
        chatHistory.addUserMessage(request.input());
        var result = response.getResult();
        chatHistory.addAssistantMessage(result);
        return result;
    }
}
