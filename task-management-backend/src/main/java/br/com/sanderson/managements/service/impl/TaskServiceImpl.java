package br.com.sanderson.managements.service.impl;

import br.com.sanderson.managements.domain.model.Project;
import br.com.sanderson.managements.domain.model.Task;
import br.com.sanderson.managements.dto.TaskRequest;
import br.com.sanderson.managements.dto.TaskResponse;
import br.com.sanderson.managements.repository.ProjectRepository;
import br.com.sanderson.managements.repository.TaskRepository;
import br.com.sanderson.managements.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;


    @Override
    public Page<TaskResponse> findTasks(Long projectId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationDate").descending());
        Page<Task> tasks = (projectId == null) ?
                taskRepository.findAll(pageable) :
                taskRepository.findTasksByProjectId(projectId, pageable);
        return tasks.map(this::buildResponse);

    }

    @Override
    public void deleteTask(Long taskId) {
        if(!taskRepository.existsById(taskId)) throw new RuntimeException("Task not found with ID: " + taskId);
        taskRepository.deleteById(taskId);
    }

    @Override
    public void saveTask(TaskRequest request) {
        taskRepository.save(buildEntity(request));
    }

    private TaskResponse buildResponse(Task entity) {
        return TaskResponse.builder()
                .taskId(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus().getValue())
                .creationDate(String.valueOf(entity.getCreationDate()))
                .projectId(entity.getProject().getId())
                .build();
    }


    private Task buildEntity(TaskRequest request) {
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .project(getProject(request))
                .build();
    }

    private Project getProject(TaskRequest request) {
        Optional<Project> project = projectRepository.findById(request.projectId());
        if(project.isEmpty() ) throw  new RuntimeException("Project not found");
        return project.get();
    }
}
