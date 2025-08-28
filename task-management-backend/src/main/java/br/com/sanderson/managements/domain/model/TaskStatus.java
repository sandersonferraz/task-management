package br.com.sanderson.managements.domain.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
    OPEN("Aberta"),
    IN_PROGRESS("Em andamento"),
    COMPLETED("Concluída"),
    CANCELLED("Cancelada");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

}
