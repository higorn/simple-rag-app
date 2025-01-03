package com.epam.training.gen.ai.domain;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class HomeAssistantPlugin {
    private final Map<Integer, Light> lights;

    public HomeAssistantPlugin() {
        lights = Map.of(
                1, Light.builder().id(1).name("Entrance Light").isOn(true).brightness(100).color("red").area(Area.ENTRANCE).build(),
                2, Light.builder().id(2).name("Kitchen Light").isOn(false).brightness(50).color("green").area(Area.KITCHEN).build(),
                3, Light.builder().id(3).name("Bedroom Light").isOn(true).brightness(75).color("blue").area(Area.BEDROOM).build(),
                4, Light.builder().id(4).name("Living Room Light").isOn(false).brightness(25).color("yellow").area(Area.LIVING_ROOM).build()
        );
    }

    @DefineKernelFunction(name = "get_lights", description = "Get a list of lights and their current state")
    public List<Light> getLights() {
        log.info("Getting lights");
        return lights.values().stream().toList();
    }

    @DefineKernelFunction(name = "change_state", description = "Changes the state of the light")
    public Light changeLightState(
            @KernelFunctionParameter(name = "id", description = "The ID of the light to change") int id,
            @KernelFunctionParameter(name = "isOn", description = "The new state of the light") boolean isOn) {
        log.info("Changing light " + id + " " + isOn);
        if (!lights.containsKey(id)) {
            throw new IllegalArgumentException("Light not found");
        }

        lights.get(id).setOn(isOn);

        return lights.get(id);
    }

    @DefineKernelFunction(name = "open_the_door", description = "Open the door")
    public boolean openTheDoor() {
        log.info("Opening the door");
        return true;
    }
}
