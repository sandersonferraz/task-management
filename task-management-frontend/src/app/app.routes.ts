import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/tasks',
    pathMatch: 'full'
  },
  {
    path: 'tasks',
    loadComponent: () => import('./app').then(m => m.App)
  },
  {
    path: '**',
    redirectTo: '/tasks'
  }
];
