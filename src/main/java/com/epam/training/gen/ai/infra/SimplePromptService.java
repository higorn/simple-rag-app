package com.epam.training.gen.ai.infra;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.epam.training.gen.ai.domain.PromptService;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class SimplePromptService implements PromptService {
    private final OpenAIAsyncClient aiAsyncClient;
    private final String deploymentName;
    private final PromptExecutionSettings promptExecutionSettings;
    private final Kernel kernel;
    private final ChatHistory chatHistory;

    public SimplePromptService(OpenAIAsyncClient aiAsyncClient,
                               @Value("${client-openai-deployment-name}") String deploymentName,
                               Map<String, PromptExecutionSettings> promptExecutionSettingsMap,
                               Kernel kernel,
                               @Value("${client-openai-execution-context}") String context) {
        this.aiAsyncClient = aiAsyncClient;
        this.deploymentName = deploymentName;
        this.promptExecutionSettings = promptExecutionSettingsMap.get(deploymentName);
        this.kernel = kernel;
        this.chatHistory = new ChatHistory();
        this.chatHistory.addSystemMessage(context);
    }

    @Override
    public List<String> getPromptCompletions(String message) {
        var completions = aiAsyncClient
                .getChatCompletions(
                        deploymentName,
                        new ChatCompletionsOptions(List.of(new ChatRequestUserMessage(message)))
                                .setTemperature(promptExecutionSettings.getTemperature()))
                .block();
        var messages = Objects.requireNonNull(completions).getChoices().stream()
                .map(c -> c.getMessage().getContent())
                .toList();
        log.info(messages.toString());
        return messages;
    }

    @Override
    public List<String> getPromptCompletionsWithHistory(String input) {
        var response = kernel.invokeAsync(getChat())
                .withArguments(getKernelFunctionArguments(input, chatHistory))
                .block();
        chatHistory.addUserMessage(input);
        chatHistory.addAssistantMessage(Objects.requireNonNull(response).getResult());
        return chatHistory.getMessages().stream().map(ChatMessageContent::getContent).toList();
    }

    private KernelFunction<String> getChat() {
        return KernelFunction.<String>createFromPrompt("""
                {{$chatHistory}}
                <message role="user">{{$request}}""")
                .build();
    }

    private KernelFunctionArguments getKernelFunctionArguments(String input, ChatHistory chatHistory) {
        return KernelFunctionArguments.builder()
                .withVariable("reqquest", input)
                .withVariable("chatHistory", chatHistory)
                .build();
    }
}
