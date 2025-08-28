package br.com.sanderson.managements.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task title is required")
    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 50, nullable = false)
    @Convert(converter = TaskStatusConverter.class)
    private TaskStatus status;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @NotNull(message = "A Project for the task is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    private Project project;

    @PrePersist
    void performPrePersist() {
        this.creationDate = LocalDateTime.now();
        if(this.status == null) {
            this.status = TaskStatus.OPEN;
        }
    }
}
