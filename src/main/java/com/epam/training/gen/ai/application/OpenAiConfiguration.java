package com.epam.training.gen.ai.application;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfiguration {
    @Bean
    public OpenAIAsyncClient openAIAsyncClient(@Value("${client-openai-key}") String apiKey,
                                               @Value("${client-openai-endpoint}") String endpoint) {
        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(apiKey))
                .endpoint(endpoint)
                .buildAsyncClient();
    }
}
