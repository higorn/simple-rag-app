package com.epam.training.gen.ai.domain;

import java.util.List;

public interface PromptService {
    List<String> getPromptCompletions(String message);

    List<String> getPromptCompletionsWithHistory(String input);
}
