package br.com.sanderson.managements.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(
        @NotBlank(message = "Task title is required")
        String title,
        String description,
        @NotNull(message = "The  Project code is required")
        Long projectId
) {
}
