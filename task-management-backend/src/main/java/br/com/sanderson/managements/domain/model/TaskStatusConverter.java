package br.com.sanderson.managements.domain.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TaskStatusConverter implements AttributeConverter<TaskStatus, String> {
    @Override
    public String convertToDatabaseColumn(TaskStatus status) {
        if (status == null) return null;
        return status.getValue();
    }

    @Override
    public TaskStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getValue().equals(dbData)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown TaskStatus: " + dbData);
    }
}
