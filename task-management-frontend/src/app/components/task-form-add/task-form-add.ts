import { Component, Input, inject } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProjectResponse } from '../../models/project.model';
import { TaskRequest } from '../../models/task.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-task-form-add',
  imports: [CommonModule, FormsModule],
  templateUrl: './task-form-add.html',
  styleUrl: './task-form-add.scss'
})
export class TaskFormAdd {
@Input() projects: ProjectResponse[] = [];

  model: TaskRequest = {
    title: '',
    description: '',
    projectId: undefined
  };

  private readonly activeModal = inject(NgbActiveModal);

  save(): void {
    if (!this.model.title || !this.model.projectId) {
      alert('Título e projeto são obrigatórios!');
      return;
    }
    this.activeModal.close(this.model);
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
