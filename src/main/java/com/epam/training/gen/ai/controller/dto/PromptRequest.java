package com.epam.training.gen.ai.controller.dto;

public record PromptRequest(String input, String modelName, double temperature, boolean allowKernelFunctions) {
}
