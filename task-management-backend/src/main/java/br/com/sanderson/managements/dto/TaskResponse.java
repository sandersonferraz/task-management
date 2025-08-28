package br.com.sanderson.managements.dto;

import lombok.Builder;

@Builder
public record TaskResponse(
        Long taskId,
        String title,
        String description,
        String status,
        String creationDate,
        Long projectId
) {
}
