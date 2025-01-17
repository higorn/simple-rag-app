package com.epam.training.gen.ai.controller.dto;

import java.util.List;

public record BookCreationResponse(String id, List<Float> vector) {
}
