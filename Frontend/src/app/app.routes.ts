// src/app/app.routes.ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'projetos',
    loadComponent: () =>
      import('./projeto/projeto.component').then(m => m.ProjetoComponent)
  },
  {
    path: 'home',
    loadComponent: () =>
      import('../mainpage/mainpage.component').then(m => m.MainpageComponent) // <- subiu um nível
  },
  { path: '', pathMatch: 'full', redirectTo: 'projetos' },
  { path: '**', redirectTo: 'projetos' }
];