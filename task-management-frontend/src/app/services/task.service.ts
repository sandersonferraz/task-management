import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { TaskRequest, TaskResponse } from '../models/task.model';
import { Page } from '../models/page.model';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
   private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/tasks';

  getTasks(projectId?: number, page: number = 0, size: number = 10): Observable<Page<TaskResponse>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (projectId) {
      params = params.set('projectId', projectId.toString());
    }

    return this.http.get<Page<TaskResponse>>(this.apiUrl, { params }).pipe(
      catchError(error => {
        console.error('Erro ao buscar tarefas:', error);
        return throwError(() => new Error('Falha ao carregar tarefas'));
      })
    );
  }

  saveTask(task: TaskRequest): Observable<void> {
    return this.http.post<void>(this.apiUrl, task).pipe(
      catchError(error => {
        console.error('Erro ao salvar tarefa:', error);
        return throwError(() => new Error('Falha ao salvar tarefa'));
      })
    );
  }

  deleteTask(taskId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${taskId}`).pipe(
      catchError(error => {
        console.error('Erro ao deletar tarefa:', error);
        return throwError(() => new Error('Falha ao deletar tarefa'));
      })
    );
  }
}
