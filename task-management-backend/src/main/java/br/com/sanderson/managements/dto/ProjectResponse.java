package br.com.sanderson.managements.dto;

import lombok.Builder;

@Builder
public record ProjectResponse(
        Long projectId,
        String name
) {
}
