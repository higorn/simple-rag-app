package com.epam.training.gen.ai.infra;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.epam.training.gen.ai.domain.PromptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SimplePromptService implements PromptService {
    private final OpenAIAsyncClient aiAsyncClient;
    private final String deploymentName;

    public SimplePromptService(OpenAIAsyncClient aiAsyncClient,
                               @Value("${client-openai-deployment-name}") String deploymentName) {
        this.aiAsyncClient = aiAsyncClient;
        this.deploymentName = deploymentName;
    }

    @Override
    public List<String> getPromptCompletions(String message) {
        var completions = aiAsyncClient
                .getChatCompletions(
                        deploymentName,
                        new ChatCompletionsOptions(
                                List.of(new ChatRequestUserMessage(message))))
                .block();
        var messages = completions.getChoices().stream()
                .map(c -> c.getMessage().getContent())
                .toList();
        log.info(messages.toString());
        return messages;
    }
}
