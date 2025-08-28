package br.com.sanderson.managements.service.impl;

import br.com.sanderson.managements.domain.model.Project;
import br.com.sanderson.managements.domain.model.Task;
import br.com.sanderson.managements.domain.model.TaskStatus;
import br.com.sanderson.managements.dto.TaskResponse;
import br.com.sanderson.managements.repository.ProjectRepository;
import br.com.sanderson.managements.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void shouldReturnTasksWhenSuccessful() {
        Project project = Project.builder()
                .id(1L)
                .name("New Project 1")
                .build();
        int page = 0, size = 2;

        Task task1 = Task.builder()
                .id(1L)
                .title("Task 1")
                .status(TaskStatus.OPEN)
                .description("New description 1")
                .creationDate(LocalDateTime.now())
                .project(project)
                .build();
        Task task2 = Task.builder().id(2L)
                .title("Task 2")
                .status(TaskStatus.OPEN)
                .description("New description 2")
                .creationDate(LocalDateTime.now().plusHours(7))
                .project(project).build();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationDate"));
        Page<Task> mockPage = new PageImpl<>(List.of(task1, task2), pageable, 2);

        when(taskRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Task> result = taskRepository.findAll(pageable);

        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getContent()).extracting(Task::getTitle)
                .containsExactly("Task 1", "Task 2");
    }

    @Test
    void shouldReturnTasksByProjectId() {
        Long projectId = 1L;
        int page = 0, size = 2;

        Project project = Project.builder()
                .id(1L)
                .name("New Project 1")
                .build();

        Task task1 = Task.builder()
                .id(1L)
                .title("Task 1")
                .status(TaskStatus.OPEN)
                .description("New description 1")
                .creationDate(LocalDateTime.now())
                .project(project)
                .build();
        Task task2 = Task.builder().id(2L)
                .title("Task 2")
                .status(TaskStatus.OPEN)
                .description("New description 2")
                .creationDate(LocalDateTime.now().plusHours(2))
                .project(project).build();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creationDate"));
        Page<Task> mockPage = new PageImpl<>(List.of(task1, task2), pageable, 2);

        when(taskRepository.findTasksByProjectId(projectId, pageable)).thenReturn(mockPage);

        Page<TaskResponse> result = taskService.findTasks(projectId, page, size);

        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getContent()).extracting(TaskResponse::title)
                .containsExactly("Task 1", "Task 2");
    }

    @Test
    void shouldDeleteTaskWhenTaskId() {
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);
        taskService.deleteTask(taskId);
        verify(taskRepository).deleteById(taskId);
    }
}
