package br.com.sanderson.managements.repository;

import br.com.sanderson.managements.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldTasksPageWhenSuccessful() {

        Page<Task> page = taskRepository.findAll(PageRequest.of(0, 10));

        assertThat(page.getTotalElements()).isEqualTo(15);
        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getContent().getFirst().getProject().getName()).isEqualTo("Plataforma de Pesquisa em IA");
        assertThat(page.getContent().getFirst().getTitle()).isEqualTo("Configurar Ambiente de Desenvolvimento");

    }


    @Test
    void shouldFindTasksByProjectIdWhenSuccessful() {
        Long projectId = 2L;

        Page<Task> page = taskRepository.findTasksByProjectId(projectId, PageRequest.of(0, 10));

        assertThat(page.getTotalElements()).isEqualTo(7);
        assertThat(page.getContent()).hasSize(7);
        assertThat(page.getContent().getLast().getProject().getName()).isEqualTo("Sistema de Gestão de Clínicas");
        assertThat(page.getContent().getLast().getTitle()).isEqualTo("Auditoria de Segurança");
    }


    @Test
    void shouldDeleteTaskWhenIdExists() {
        Long taskId = 1L;

        Task task = taskRepository.findById(taskId).orElseThrow();
        taskRepository.delete(task);

        assertThat(taskRepository.findById(taskId)).isEmpty();
    }

    @Test
    void shouldFailDeleteTaskWhenIdDoesNotExist() {
        Long taskId = 100L;
        assertThat(taskRepository.findById(taskId)).isEmpty();
    }
}
