package com.epam.training.gen.ai.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Light {
    private int id;
    private String name;
    private boolean isOn;
    private int brightness;
    private String color;
    private Area area;
}
