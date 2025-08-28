package br.com.sanderson.managements.repository;

import br.com.sanderson.managements.domain.model.Project;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void shouldSaveProjectWhenSuccessful() {
        Project project = Project.builder()
                .name("New Project")
                .build();

        Project saved = projectRepository.save(project);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("New Project");
    }

    @Test
    void shouldThrowConstraintViolationExceptionWhenNameIsBlank() {
        Project project = Project.builder().name("").build();

        assertThatThrownBy(() -> projectRepository.saveAndFlush(project))
                .isInstanceOf(ConstraintViolationException.class)
                .satisfies(exception -> {
                    ConstraintViolationException ex = (ConstraintViolationException) exception;
                    Assertions.assertThat(ex.getConstraintViolations()).hasSize(1);
                    Assertions.assertThat(ex.getConstraintViolations().iterator().next().getMessage())
                            .isEqualTo("Project name is required");
                });
    }
}
