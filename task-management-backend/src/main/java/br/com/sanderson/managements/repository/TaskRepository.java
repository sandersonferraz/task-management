package br.com.sanderson.managements.repository;

import br.com.sanderson.managements.domain.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Long> {
    Page<Task> findTasksByProjectId(Long projectId, Pageable pageable);
}
