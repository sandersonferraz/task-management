import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbModal, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

import { Page } from '../../models/page.model';
import { ProjectResponse } from '../../models/project.model';
import { TaskRequest, TaskResponse } from '../../models/task.model';
import { ProjectService } from '../../services/project.service';
import { TaskService } from '../../services/task.service';
import { TaskForm } from '../task-form/task-form';
import { TaskFormAdd } from '../task-form-add/task-form-add';

@Component({
  selector: 'app-task-list',
  imports: [CommonModule, FormsModule, NgbModalModule],
  templateUrl: './task-list.html',
  styleUrl: './task-list.scss'
})
export class TaskList implements OnInit {
 private readonly taskService = inject(TaskService);
  private readonly projectService = inject(ProjectService);
    private readonly modalService = inject(NgbModal);

  tasks = signal<TaskResponse[]>([]);
  projects = signal<ProjectResponse[]>([]);
  currentPage = signal(0);
  pageSize = signal(10);
  totalElements = signal(0);
  totalPages = signal(0);
  selectedProjectId = signal<number | undefined>(undefined);
  isLoading = signal(false);
  isLoadingProjects = signal(false);

  hasNoTasks = computed(() => !this.isLoading() && this.tasks().length === 0);
  hasProjects = computed(() => this.projects().length > 0);
  showPagination = computed(() => !this.isLoading() && this.totalPages() > 1);

  pageNumbers = computed(() => {
    const current = this.currentPage();
    const total = this.totalPages();
    const pages = [];
    const startPage = Math.max(0, current - 2);
    const endPage = Math.min(total - 1, current + 2);

    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }
    return pages;
  });

  ngOnInit(): void {
    this.loadProjects();
    this.loadTasks();
  }

  loadProjects(): void {
    this.isLoadingProjects.set(true);

    this.projectService.getProjects().subscribe({
      next: (projects) => {
        this.projects.set(projects);
        console.log(this.projects)
        this.isLoadingProjects.set(false);
      },
      error: (error) => {
        console.error('Erro ao carregar projetos:', error);
        this.isLoadingProjects.set(false);
      }
    });
  }

  loadTasks(): void {
    this.isLoading.set(true);

    this.taskService.getTasks(
      this.selectedProjectId(),
      this.currentPage(),
      this.pageSize()
    ).subscribe({
      next: (page: Page<TaskResponse>) => {
        this.tasks.set(page.content);
        this.totalElements.set(page.totalElements);
        this.totalPages.set(page.totalPages);
        this.isLoading.set(false);
      },
      error: (error) => {
        console.error('Erro ao carregar tarefas:', error);
        this.isLoading.set(false);
      }
    });
  }

  onProjectFilterChange(projectId: string): void {
    const id = projectId ? Number(projectId) : undefined;
    this.selectedProjectId.set(id);
    this.currentPage.set(0);
    this.loadTasks();
  }

  onPageChange(page: number): void {
    this.currentPage.set(page);
    this.loadTasks();
  }

  onPageSizeChange(size: string): void {
    this.pageSize.set(Number(size));
    this.currentPage.set(0);
    this.loadTasks();
  }

  deleteTask(taskId: number): void {
    if (confirm('Tem certeza que deseja excluir esta tarefa?')) {
      this.taskService.deleteTask(taskId).subscribe({
        next: () => {
          if (this.tasks().length === 1 && this.currentPage() > 0) {
            this.currentPage.update(page => page - 1);
          }
          this.loadTasks();
        },
        error: (error) => {
          console.error('Erro ao excluir tarefa:', error);
          alert('Erro ao excluir tarefa. Tente novamente.');
        }
      });
    }
  }

  getProjectName(projectId: number): string {
    const project = this.projects().find(p => p.projectId === projectId);
    return project ? project.name : 'Projeto não encontrado';
  }

  refreshTasks(): void {
    this.loadTasks();
  }

  trackByTaskId = (index: number, task: TaskResponse): number => task.taskId;
  trackByProjectId = (index: number, project: ProjectResponse): number => project.projectId;

addTask(): void {
    const modalRef = this.modalService.open(TaskFormAdd, { size: 'lg' });
    modalRef.componentInstance.projects = this.projects();
    modalRef.result.then((task?: TaskRequest) => {
      if (task) this.taskService.saveTask(task).subscribe(() => this.loadTasks());
    }).catch(() => {});
  }
}
