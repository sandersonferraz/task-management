package br.com.sanderson.managements.domain.model;

import br.com.sanderson.managements.domain.model.Project;
import br.com.sanderson.managements.domain.model.Task;
import br.com.sanderson.managements.domain.model.TaskStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskTest {

    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        Task task = Task.builder()
                .title("New Task")
                .description("Sem descrição")
                .status(TaskStatus.OPEN)
                .creationDate(LocalDateTime.now())
                .project(new Project())
                .build();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailWhenTitleIsBlank() {
        Task task = Task.builder()
                .title("")
                .description("Sem descrição")
                .status(TaskStatus.OPEN)
                .creationDate(LocalDateTime.now())
                .project(new Project())
                .build();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Task title is required");
    }

    @Test
    void shouldSaveWithDefaultStatusWhenStatusIsNull() {
        Task task = Task.builder()
                .title("New Task")
                .description("Sem descrição")
                .status(null)
                .creationDate(LocalDateTime.now())
                .project(new Project())
                .build();
        task.performPrePersist();
        assertThat(task.getStatus()).isEqualTo(TaskStatus.OPEN);
    }

    @Test
    void shouldSaveWithCurrentDateWhenCreationDateIsNull() {
        Task task = Task.builder()
                .title("New Task")
                .description("Sem descrição")
                .status(TaskStatus.IN_PROGRESS)
                .creationDate(null)
                .project(new Project())
                .build();
        task.performPrePersist();
        assertThat(task.getCreationDate()).isNotNull();
    }

    @Test
    void shouldFailWhenProjectIsNull() {
        Task task = Task.builder()
                .title("New Task")
                .description("Sem descrição")
                .status(TaskStatus.OPEN)
                .creationDate(LocalDateTime.now())
                .project(null)
                .build();
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("A Project for the task is required");
    }
}
