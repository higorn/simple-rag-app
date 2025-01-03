package com.epam.training.gen.ai.controller.dto;

import javax.annotation.Nullable;

public record PromptRequest(String input, @Nullable String modelName, @Nullable Double temperature, @Nullable Boolean allowKernelFunctions) {
}
