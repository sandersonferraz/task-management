package br.com.sanderson.managements.domain.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateProjectSuccessfully() {
        Project project = Project.builder()
                .name("New Project")
                .build();
        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        Project project = Project.builder()
                .name("")
                .build();
        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Project name is required");
    }

    @Test
    void shouldFailWhenNameIsNull() {
        Project project = Project.builder()
                .name(null)
                .build();
        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        assertThat(violations).isNotEmpty();
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Project name is required");
    }

}
