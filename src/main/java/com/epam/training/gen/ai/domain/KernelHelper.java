package com.epam.training.gen.ai.domain;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.epam.training.gen.ai.application.SimplePlugin;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;

public class KernelHelper {

    public static Kernel getKernel(String modelName, OpenAIAsyncClient aiAsyncClient) {
        return Kernel.builder()
                .withAIService(ChatCompletionService.class, getChatCompletionService(modelName, aiAsyncClient))
                .withPlugin(getKernelPlugin())
                .build();
    }

    private static ChatCompletionService getChatCompletionService(String modelName, OpenAIAsyncClient aiAsyncClient) {
        return OpenAIChatCompletion.builder()
                .withModelId(modelName)
                .withOpenAIAsyncClient(aiAsyncClient)
                .build();
    }

    private static KernelPlugin getKernelPlugin() {
        return KernelPluginFactory.createFromObject(new SimplePlugin(), "Simple Plugin");
    }

    public static KernelFunction<String> getChat() {
        return KernelFunction.<String>createFromPrompt("""
                {{$chatHistory}}
                <message role="user">{{$request}}</message>""")
                .build();
    }

    public static KernelFunctionArguments getKernelFunctionArguments(String input, ChatHistory chatHistory) {
        return KernelFunctionArguments.builder()
                .withVariable("request", input)
                .withVariable("chatHistory", chatHistory)
                .build();
    }

    public static PromptExecutionSettings getPromptExecutionSettings(double temperature) {
        return PromptExecutionSettings.builder().withTemperature(temperature).build();
    }

    public static InvocationContext getInvocationContext(ToolCallBehavior callBehavior) {
        return InvocationContext.builder()
                .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
                .withToolCallBehavior(callBehavior)
                .build();
    }
}
