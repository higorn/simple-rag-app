package com.epam.training.gen.ai.domain;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.epam.training.gen.ai.controller.dto.PromptRequest;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.epam.training.gen.ai.domain.KernelHelper.*;

@Service
@RequiredArgsConstructor
public class GenericPromptService {
    private final OpenAIAsyncClient aiAsyncClient;
    private final ChatHistory chatHistory;
    @Value("${client-openai-deployment-name}")
    private String defaultModelName;
    @Value("${client-openai-execution-temperature}")
    private double defaultTemperature;

    public String getResponse(PromptRequest request) {
        var kernel = getKernel(Optional.ofNullable(request.modelName()).orElse(defaultModelName), aiAsyncClient);
        var response = kernel.invokeAsync(getChat())
                .withArguments(getKernelFunctionArguments(request.input(), chatHistory))
                .withPromptExecutionSettings(getPromptExecutionSettings(request.temperature() == 0
                        ? defaultTemperature
                        : request.temperature()))
                .withInvocationContext(getInvocationContext(request.allowKernelFunctions()
                        ? ToolCallBehavior.allowAllKernelFunctions(true)
                        : null))
                .block();
        var result = Objects.requireNonNull(response).getResult();
        chatHistory.addUserMessage(request.input());
        chatHistory.addAssistantMessage(result);
        return result;
    }
}
