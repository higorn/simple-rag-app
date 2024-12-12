package com.epam.training.gen.ai.application;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemanticKernelConfiguration {
    @Bean
    public Kernel kernel(ChatCompletionService chatCompletionService, KernelPlugin kernelPlugin) {
        return Kernel.builder()
                .withAIService(ChatCompletionService.class, chatCompletionService)
                .withPlugin(kernelPlugin)
                .build();
    }

    @Bean
    public ChatCompletionService chatCompletionService(
            @Value("${client-openai-deployment-name}") String deploymentName,
            OpenAIAsyncClient aiAsyncClient) {
        return OpenAIChatCompletion.builder()
                .withModelId(deploymentName)
                .withOpenAIAsyncClient(aiAsyncClient)
                .build();
    }

    @Bean
    public KernelPlugin kernelPlugin() {
        return KernelPluginFactory.createFromObject(new SimplePlugin(), "Simple Plugin");
    }

    @Bean
    public ChatHistory chatHistory() {
        return new ChatHistory();
    }
}
