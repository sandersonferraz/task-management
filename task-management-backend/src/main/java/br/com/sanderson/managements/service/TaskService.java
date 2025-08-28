package br.com.sanderson.managements.service;

import br.com.sanderson.managements.dto.TaskRequest;
import br.com.sanderson.managements.dto.TaskResponse;
import org.springframework.data.domain.Page;

public interface TaskService {
    Page<TaskResponse> findTasks(Long projectId, int page, int size);

    void deleteTask(Long taskId);

    void saveTask(TaskRequest request);
}
