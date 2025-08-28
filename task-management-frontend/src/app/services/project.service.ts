import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { ProjectResponse } from '../models/project.model';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
   private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/projects';

  getProjects(): Observable<ProjectResponse[]> {
    return this.http.get<ProjectResponse[]>(this.apiUrl).pipe(
      catchError(error => {
        console.error('Erro ao buscar projetos:', error);
        return throwError(() => new Error('Falha ao carregar projetos'));
      })
    );
  }
}
