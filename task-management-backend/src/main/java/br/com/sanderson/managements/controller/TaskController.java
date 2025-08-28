package br.com.sanderson.managements.controller;

import br.com.sanderson.managements.dto.TaskRequest;
import br.com.sanderson.managements.dto.TaskResponse;
import br.com.sanderson.managements.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getTasks(@RequestParam(required = false) Long projectId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.findTasks(projectId, page, size));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> saveTask(@RequestBody TaskRequest request) {
        taskService.saveTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
