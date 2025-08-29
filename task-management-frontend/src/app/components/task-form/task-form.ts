import { CommonModule } from '@angular/common';
import { Component, computed, EventEmitter, inject, Output, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { ProjectResponse } from '../../models/project.model';
import { TaskRequest } from '../../models/task.model';
import { ProjectService } from '../../services/project.service';
import { TaskService } from '../../services/task.service';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './task-form.html',
  styleUrl: './task-form.scss'
})
export class TaskForm {
@Output() taskCreated = new EventEmitter<void>();

  private readonly fb = inject(FormBuilder);
  private readonly projectService = inject(ProjectService);
  private readonly taskService = inject(TaskService);

  projects = signal<ProjectResponse[]>([]);
  isSubmitting = signal(false);
  isLoadingProjects = signal(false);

  taskForm: FormGroup;

  hasProjects = computed(() => this.projects().length > 0);
  canSubmit = computed(() => this.taskForm?.valid && !this.isSubmitting());

  constructor() {
    this.taskForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      description: ['', [Validators.maxLength(500)]],
      projectId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects(): void {
    this.isLoadingProjects.set(true);

    this.projectService.getProjects().subscribe({
      next: (projects) => {
        this.projects.set(projects);
        this.isLoadingProjects.set(false);
      },
      error: (error) => {
        console.error('Erro ao carregar projetos:', error);
        this.isLoadingProjects.set(false);
      }
    });
  }

  onSubmit(): void {
    if (this.canSubmit()) {
      this.isSubmitting.set(true);

      const taskData: TaskRequest = {
        title: this.taskForm.value.title.trim(),
        description: this.taskForm.value.description?.trim() || undefined,
        projectId: Number(this.taskForm.value.projectId)
      };

      this.taskService.saveTask(taskData).subscribe({
        next: () => {
          this.taskForm.reset();
          this.taskCreated.emit();
          this.isSubmitting.set(false);
        },
        error: (error) => {
          console.error('Erro ao criar tarefa:', error);
          this.isSubmitting.set(false);
        }
      });
    }
  }

  get title() { return this.taskForm.get('title'); }
  get description() { return this.taskForm.get('description'); }
  get projectId() { return this.taskForm.get('projectId'); }

    trackByProjectId = (index: number, project: ProjectResponse): number => project.projectId;

}
