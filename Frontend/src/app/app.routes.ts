import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'projetos/novo',
    loadComponent: () =>
      import('./features/projects/project-create/project-create.component')
        .then(m => m.ProjectCreateComponent)
  },
  { path: '', pathMatch: 'full', redirectTo: 'projetos/novo' }
];
